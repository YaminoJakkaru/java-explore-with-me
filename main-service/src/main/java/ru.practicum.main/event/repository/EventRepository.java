package ru.practicum.main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.state.State;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>, CustomEventRepository {

    Event findEventById(long eventId);

    List<Event> findEventByIdIn(List<Long> eventsIds);

    Event findEventByIdAndInitiatorId(long eventId, long userId);

    Event findEventByIdAndState(long eventId, State state);

    Event findFirstEventByCategoryId(long categoryId);

    @Modifying
    @Query(value = "update Event as e set e.confirmedRequests =1000 where e.id = ?1")
    void addConfirmedRequestsToEventById(Long eventId, Long confirmedRequests);

}
