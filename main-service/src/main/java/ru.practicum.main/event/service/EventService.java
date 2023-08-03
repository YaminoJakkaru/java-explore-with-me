package ru.practicum.main.event.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.dto.*;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.State;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventFullDto addEvent(long userId, NewEventDto newEventDto);

    EventFullDto updateEvent(long userId, long eventId, PublicUpdateEventDto updateEventDto);

    EventFullDto updateEvent(long eventId, AdminUpdateEventDto updateEventDto);

    EventFullDto findEventById(long userId, long eventId);

    EventFullDto findEventById(long eventId, HttpServletRequest request);

    List<EventFullDto> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                                  List<Long> categoryIds, Pageable pageable);

    List<EventShortDto> findUserEvents(long userId, Pageable pageable);

    List<EventShortDto> findPublishedEvents(LocalDateTime rangeStart, LocalDateTime rangeEnd, List<Long> categories,
                                            String text, Boolean paid, Boolean onlyAvailable, EventsSort eventsSort,
                                            Pageable pageable, HttpServletRequest request);


}
