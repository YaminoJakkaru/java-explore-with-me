package ru.practicum.main.request.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.category.CategoryRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.event.state.State;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.request.RequestRepository;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.model.Request;
import ru.practicum.main.request.service.RequestService;
import ru.practicum.main.request.status.Status;
import ru.practicum.main.user.UserRepository;
import ru.practicum.main.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    public RequestServiceImpl(EventRepository eventRepository, UserRepository userRepository, RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }


    @Override
    public RequestDto addRequest(long userId, long eventId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + " was not found");
        }
        Event event = eventRepository.findEventByIdAndState(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundException("Event with id= " + eventId + " was not found");
        }
        if (event.getInitiator().getId() == userId) {
            throw new DataValidationException("You cannot submit a request to participate in your event");
        }
        if (event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new DataValidationException("You cannot exceed the limit");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new DataValidationException("Нou cannot participate in an unpublished event");
        }
        Request request = new Request()
                .setRequester(user)
                .setEvent(event)
                .setStatus(event.isRequestModeration() ? Status.PENDING : Status.CONFIRMED);
          return  requestRepository.save(request).toRequestDto();
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult changeRequestsStatus(long userId, long eventId, List<Long> requestsIds,
                                                               Status status) {
        Event event = eventRepository.findEventByIdAndState(eventId, State.PUBLISHED);
        if (event == null || event.getInitiator().getId() != userId) {
            throw new NotFoundException("Event with id= " + eventId + " was not found");
        }
        if(event.getConfirmedRequests() + requestsIds.size() > event.getParticipantLimit()) {
            throw new DataValidationException("The participant limit has been reached");
        }
        Map<Long, RequestDto> requests = requestRepository.findRequestsByEventId(eventId)
                .stream().collect(Collectors.toMap(Request::getId, Request::toRequestDto));
        if (requests.values().stream().anyMatch(request ->  requestsIds.contains(request.getId())
                && !request.getStatus().equals(Status.PENDING))) {
            throw new DataValidationException("Request must have status PENDING");
        }
        if (!requests.keySet().containsAll(requestsIds)) {
            throw new DataValidationException("One or more requests not found");
        }
        requestRepository.updateRequestStatusByIdIn(requestsIds, status);
        requestsIds.forEach(id -> requests.get(id).setStatus(status));
        if (status.equals(Status.CONFIRMED)) {
            eventRepository.addConfirmedRequestsToEventById(requestsIds);
            if (event.getConfirmedRequests() + requestsIds.size() == event.getParticipantLimit()) {
                requestRepository.updateRequestStatusByEventId(eventId, Status.PENDING, Status.REJECTED);
                for (RequestDto requestDto : requests.values()) {
                    if ( requestDto.getStatus().equals(Status.PENDING)) {
                        requestDto.setStatus(Status.REJECTED);
                    }
                }
            }
        }
        return new EventRequestStatusUpdateResult().
                setConfirmedRequests(requests.values().stream()
                        .filter(requestDto -> requestDto.getStatus().equals(Status.CONFIRMED))
                        .collect(Collectors.toList()))
                .setRejectedRequests(requests.values().stream()
                        .filter(requestDto -> requestDto.getStatus().equals(Status.REJECTED))
                        .collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public RequestDto cancelRequestsStatus(long userId, long requestId) {
        Request request = requestRepository.findRequestById(requestId);
        if (request == null || request.getRequester().getId() != userId) {
            throw new DataValidationException("Request with id "+ requestId + " was not found");
        }
        requestRepository.updateRequestStatusById(requestId, Status.REJECTED);
        request.setStatus(Status.REJECTED);
        return request.toRequestDto();
    }

    @Override
    public List<RequestDto> findRequestsByEventInitiatorId(long userId, long eventId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + " was not found");
        }
        return  requestRepository.findRequestsByEventId(eventId)
                .stream()
                .map(Request::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> findRequestsByRequesterId(long userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + " was not found");
        }
        return  requestRepository.findRequestsByRequesterId(userId)
                .stream()
                .map(Request::toRequestDto)
                .collect(Collectors.toList());
    }


}
