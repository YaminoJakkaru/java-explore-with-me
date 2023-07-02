package ru.practicum.main.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.State;


import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventDto addEvent(long userId, NewEventDto newEventDto);

    EventDto updateEvent(long userId, long eventId, UpdateEventDto updateEventDto);

    EventDto updateEvent(long eventId, UpdateEventDto updateEventDto);

    EventDto findEventById(long userId, long eventId);

    EventDto findEventById(long eventId);

    List<EventDto> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                              List<Long> categoryIds, Pageable pageable);

    List<EventShortDto> findUserEvents(long userId, Pageable pageable);

    List<EventShortDto> findPublishedEvents(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories, String text,
                              Boolean paid, Boolean onlyAvailable, EventsSort eventsSort, Pageable pageable);


}
