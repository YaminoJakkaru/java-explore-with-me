package ru.practicum.main.event.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.State;
import ru.practicum.main.event.state.StateAction;
import ru.practicum.main.category.CategoryRepository;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.request.RequestRepository;
import ru.practicum.main.user.UserRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.exception.NotFoundException;

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
    private final RequestRepository requestRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                            CategoryRepository categoryRepository, RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.requestRepository = requestRepository;

    }


    @Override
    @Transactional
    public EventDto addEvent(long userId, NewEventDto newEventDto) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new DataValidationException("Field: eventDate. Error:" +
                    " должно содержать дату, которая еще не наступила. Value:" + newEventDto.getEventDate().toString());
        }
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + "was not found");
        }
        Category category = categoryRepository.findCategoryById(newEventDto.getCategoryId());
        if (category == null) {
            throw new DataValidationException("Category with id=" + newEventDto.getCategoryId() + "was not found");
        }
        return eventRepository.save(newEventDto.toEvent().setInitiator(user).setCategory(category)).toEventDto();
    }

    @Override
    @Transactional
    public EventDto updateEvent(long userId, long eventId, UpdateEventDto updateEventDto) {
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
        if (!updateEventDto.getAnnotation().isBlank()) {
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
        if (!updateEventDto.getDescription().isBlank()) {
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
            if (updateEventDto.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                throw new DataValidationException("you can't publish event");
            }
            if (!event.getState().equals(State.PENDING)) {
                throw new DataValidationException("you can't change State" + event.getState() + " event");
            }
            event.setState(State.CANCELED);
        }
        if (!updateEventDto.getTitle().isBlank()) {
            event.setTitle(updateEventDto.getTitle());
        }
        return eventRepository.save(event).toEventDto();
    }

    @Override
    @Transactional
    public EventDto updateEvent(long eventId, UpdateEventDto updateEventDto) {
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new DataValidationException("Event with id=" + eventId + "was not found");
        }
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new DataValidationException("It's too late to make changes");
        }
        if (!updateEventDto.getAnnotation().isBlank()) {
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
        if (!updateEventDto.getDescription().isBlank()) {
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
            event.setPaid(updateEventDto.getRequestModeration());
        }
        if (updateEventDto.getStateAction() != null) {
            if (!event.getState().equals(State.PENDING)) {
                throw new DataValidationException("you can't change State" + event.getState() + " event");
            }
            event.setState(updateEventDto.getStateAction().equals(StateAction.PUBLISH_EVENT) ? State.PUBLISHED
                    : State.CANCELED);
        }
        if (!updateEventDto.getTitle().isBlank()) {
            event.setTitle(updateEventDto.getTitle());
        }
        log.info("Changed event data: " + event);
        return eventRepository.save(event).toEventDto();
    }

    @Override
    public EventDto findEventById(long userId, long eventId) {
        Event event = eventRepository.findEventByIdAndInitiatorId(eventId, userId);
        if (event == null) {
            throw new NotFoundException("Event with id= " + eventId + " was not found");
        }
        return event.toEventDto();
    }

    @Override
    public EventDto findEventById(long eventId) {
        Event event = eventRepository.findEventByIdAndState(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundException("Event with id= " + eventId + " was not found");
        }
        eventRepository.addViewToEventById(eventId);
        return event.toEventDto();
    }

    @Override
    public List<EventDto> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                                     List<Long> categoryIds, Pageable pageable) {
        if(states == null) {
            states = new ArrayList<>(EnumSet.allOf(State.class));
        }
        return eventRepository.findEvents(start, end, userIds, states, categoryIds, null, null,
                null, pageable, null).stream().map(Event::toEventDto).collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> findUserEvents(long userId, Pageable pageable) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + "was not found");
        }

        List<State> states = new ArrayList<>(EnumSet.allOf(State.class));
        return eventRepository.findEvents(LocalDateTime.MIN, LocalDateTime.MAX, List.of(userId), states,
                        null, null, null, null, pageable, null)
                .stream().map(Event::toEventShortDto).collect(Collectors.toList());
    }

    @Override
    public List<EventShortDto> findPublishedEvents(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories,
                                     String text, Boolean paid, Boolean onlyAvailable, EventsSort eventsSort,
                                     Pageable pageable) {
        List<State> states = List.of(State.PUBLISHED);
        List<EventShortDto> eventDtos = eventRepository.findEvents(LocalDateTime.MIN, LocalDateTime.MAX, null, states,
                        categories, text, paid, onlyAvailable, pageable, eventsSort)
                .stream().map(Event::toEventShortDto).collect(Collectors.toList());
        eventDtos.forEach(event -> eventRepository.addViewToEventById(event.getId()));
        return eventDtos;
    }
}
