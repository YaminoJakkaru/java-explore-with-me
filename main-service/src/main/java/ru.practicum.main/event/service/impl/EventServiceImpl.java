package ru.practicum.main.event.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.client.EventHitClient;
import ru.practicum.main.event.client.EventStatsClient;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.AdminStateAction;
import ru.practicum.main.event.state.State;
import ru.practicum.main.event.state.UserStateAction;
import ru.practicum.main.category.CategoryRepository;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.user.UserRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventStatsClient eventStatsClient;
    private final EventHitClient eventHitClient;


    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                            CategoryRepository categoryRepository, EventStatsClient eventStatsClient, EventHitClient eventHitClient) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.eventStatsClient = eventStatsClient;
        this.eventHitClient = eventHitClient;
    }


    @Override
    @Transactional
    public EventFullDto addEvent(long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DataValidationException("Field: eventDate. Error:" +
                    " должно содержать дату, которая еще не наступила. Value:" + newEventDto.getEventDate().toString());
        }
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + "was not found");
        }
        Category category = categoryRepository.findCategoryById(newEventDto.getCategory());
        if (category == null) {
            throw new DataValidationException("Category with id=" + newEventDto.getCategory() + "was not found");
        }
        EventFullDto eventFullDto = eventRepository.save(newEventDto.toEvent().setInitiator(user).setCategory(category))
                .toEventFullDto();
        log.info("add Event " + eventFullDto.getId());
        return eventFullDto;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, long eventId, PublicUpdateEventDto updateEventDto) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new DataValidationException("Event with id=" + eventId + "was not found");
        }
        if (userId != event.getInitiator().getId()) {
            throw new DataValidationException("User with id=" + userId + "has no rights");
        }
        if (event.getState() == State.PUBLISHED) {
            throw new DataValidationException("Event must not be published");
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DataValidationException("It's too late to make changes");
        }
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getCategoryId() != null
                && updateEventDto.getCategoryId() != event.getCategory().getId()) {
            Category category = categoryRepository.findCategoryById(updateEventDto.getCategoryId());
            if (category == null) {
                throw new DataValidationException("Category with id=" + updateEventDto.getCategoryId()
                        + "was not found");
            }
            event.setCategory(category);
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getEventDate() != null) {
            if (updateEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new DataValidationException("User with id=" + userId + "has no rights");
            }
            event.setEventDate(updateEventDto.getEventDate());
        }
        if (updateEventDto.getLocation() != null) {
            event.setLat(updateEventDto.getLocation().getLat());
            event.setLon(updateEventDto.getLocation().getLon());
        }
        if (updateEventDto.getPaid() != null) {
            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getRequestModeration() != null) {
            event.setPaid(updateEventDto.getRequestModeration());
        }
        if (updateEventDto.getStateAction() != null) {
              event.setState(updateEventDto.getStateAction().equals(UserStateAction.SEND_TO_REVIEW) ? State.PENDING
                      : State.CANCELED);


        }
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
        log.info("user" + userId + " changed event data: " + event.getId());
        EventFullDto eventFullDto = eventRepository.save(event).toEventFullDto();
        eventStatsClient.setViews(eventFullDto);
        return eventFullDto;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long eventId, AdminUpdateEventDto updateEventDto) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new DataValidationException("Event with id=" + eventId + "was not found");
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new DataValidationException("It's too late to make changes");
        }
        if (updateEventDto.getAnnotation() != null) {
            event.setAnnotation(updateEventDto.getAnnotation());
        }
        if (updateEventDto.getCategoryId() != null
                && updateEventDto.getCategoryId() != event.getCategory().getId()) {
            Category category = categoryRepository.findCategoryById(updateEventDto.getCategoryId());
            if (category == null) {
                throw new DataValidationException("Category with id=" + updateEventDto.getCategoryId()
                        + "was not found");
            }
            event.setCategory(category);
        }
        if (updateEventDto.getDescription() != null) {
            event.setDescription(updateEventDto.getDescription());
        }
        if (updateEventDto.getEventDate() != null) {
            event.setEventDate(updateEventDto.getEventDate());
        }
        if (updateEventDto.getLocation() != null) {
            event.setLat(updateEventDto.getLocation().getLat());
            event.setLon(updateEventDto.getLocation().getLon());
        }
        if (updateEventDto.getPaid() != null) {

            event.setPaid(updateEventDto.getPaid());
        }
        if (updateEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventDto.getParticipantLimit());
        }
        if (updateEventDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventDto.getRequestModeration());
        }
        if (updateEventDto.getStateAction() != null) {
            if (!event.getState().equals(State.PENDING)) {
                throw new DataValidationException("you can't change State" + event.getState() + " event");
            }
            event.setState(State.CANCELED);
            if (updateEventDto.getStateAction().equals(AdminStateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
        }
        if (updateEventDto.getTitle() != null) {
            event.setTitle(updateEventDto.getTitle());
        }
        EventFullDto eventFullDto = eventRepository.save(event).toEventFullDto();
        eventStatsClient.setViews(eventFullDto);
        log.info("Admin changed event data: " + event.getId());
        return eventFullDto;
    }

    @Override
    public EventFullDto findEventById(long userId, long eventId) {
        Event event = eventRepository.findEventByIdAndInitiatorId(eventId, userId);
        if (event == null) {
            throw new NotFoundException("Event with id = " + eventId + " was not found");
        }
        EventFullDto eventFullDto = event.toEventFullDto();
        eventStatsClient.setViews(eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto findEventById(long eventId, HttpServletRequest request) {
        Event event = eventRepository.findEventByIdAndState(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundException("Event with id = " + eventId + " was not found");
        }
        EventFullDto eventFullDto =  event.toEventFullDto();
        eventStatsClient.setViews(eventFullDto);
        eventHitClient.saveEndpoint("/events/" + event.getId(), request.getRemoteAddr());
        return eventFullDto;
    }

    @Override
    public List<EventFullDto> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                                         List<Long> categoryIds, Pageable pageable) {
        if(states == null) {
            states = new ArrayList<>(EnumSet.allOf(State.class));
        }
        List<EventFullDto> eventsFullDtos = eventRepository.findEvents(start, end, userIds, states, categoryIds, null, null,
                null, pageable, null).stream().map(Event::toEventFullDto)
                .collect(Collectors.toList());
        eventsFullDtos.forEach(eventStatsClient::setViews);
        return eventsFullDtos;
    }

    @Override
    public List<EventShortDto> findUserEvents(long userId, Pageable pageable) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + "was not found");
        }

        List<State> states = new ArrayList<>(EnumSet.allOf(State.class));
        List<EventShortDto> eventsShortDtos = eventRepository.findEvents(null, null, List.of(userId), states,
                        null, null, null, null, pageable, null)
                .stream().map(Event::toEventShortDto).collect(Collectors.toList());
        eventsShortDtos.forEach(eventStatsClient::setViews);
        return eventsShortDtos;
    }

    @Override
    public List<EventShortDto> findPublishedEvents(LocalDateTime start, LocalDateTime end, List<Long> categories,
                                     String text, Boolean paid, Boolean onlyAvailable, EventsSort eventsSort,
                                     Pageable pageable, HttpServletRequest request) {
        List<State> states = List.of(State.PUBLISHED);
        List<EventShortDto> eventsShortDtos = eventRepository.findEvents(start, end, null, states,
                        categories, text, paid, onlyAvailable, pageable, eventsSort)
                .stream().map(Event::toEventShortDto).collect(Collectors.toList());
        eventsShortDtos.forEach(eventStatsClient::setViews);
        eventHitClient.saveEndpoint("/events", request.getRemoteAddr());
        if(eventsSort.equals(EventsSort.VIEWS)) {
            eventsShortDtos.sort((e1, e2) -> (int) (e1.getViews() - e2.getViews()));
        }
        return eventsShortDtos;

    }
}
