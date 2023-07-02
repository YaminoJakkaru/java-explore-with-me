package ru.practicum.main.event.repository;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.CustomEventRepository;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.State;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final String BASIC_QUERY = "select e from Event as e where e.eventDate between :start and :end " +
            " and e.state in :states";
    private final String CATEGORY_CLAUSE = "and e.category.id in :categoryIds ";
    private final String INITIATOR_ID_CLAUSE = "and e.initiator.id in :userIds ";
    private final String TEXT_CLAUSE = "and (e.annotation ilike :text or e.description ilike :text) ";
    private final String PAID_CLAUSE = "and e.paid = :paid ";
    private final String ONLY_AVAILABLE_CLAUSE = "and e.confirmedRequests < e.participantLimit ";

    private final String DATE_SORT = "oder by e.eventDate asc";
    private final String VIEWS_SORT= "oder by e.views desc";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                                  List<Long> categoryIds, String text, Boolean paid, Boolean onlyAvailable,
                                  Pageable pageable, EventsSort eventsSort) {
        String query = BASIC_QUERY;
        if(userIds != null) {
            query += INITIATOR_ID_CLAUSE;
        }
        if(categoryIds != null) {
            query +=  CATEGORY_CLAUSE;
        }
        if(text != null) {
            query +=  TEXT_CLAUSE;
        }
        if (paid != null) {
            query += PAID_CLAUSE;
        }
        if (onlyAvailable != null && onlyAvailable) {
            query += ONLY_AVAILABLE_CLAUSE;
        }
       query += eventsSort != null && eventsSort.equals(EventsSort.VIEWS) ? VIEWS_SORT : DATE_SORT;

        return entityManager.createQuery(query, Event.class)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("states", states)
                .setParameter("categoryIds", categoryIds)
                .setParameter("userIds", userIds)
                .setParameter("text", text)
                .setParameter("paid", paid)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }


}
