package ru.practicum.main.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.State;

import java.time.LocalDateTime;
import java.util.List;


public interface CustomEventRepository {


    List<Event> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                           List<Long> categoryIds, String text, Boolean paid, Boolean onlyAvailable,
                           Pageable pageable, EventsSort eventsSort);


}
