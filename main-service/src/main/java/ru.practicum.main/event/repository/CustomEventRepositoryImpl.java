package ru.practicum.main.event.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.CustomEventRepository;
import ru.practicum.main.event.sort.EventsSort;
import ru.practicum.main.event.state.State;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CustomEventRepositoryImpl implements CustomEventRepository {
    private final String BASIC_QUERY = "select e from Event as e where e.state in :states ";
    private final String START_CLAUSE = "and e.eventDate >= :start ";
    private final String END_CLAUSE = "and e.eventDate <= :end ";
    private final String CATEGORY_CLAUSE = "and e.category.id in :categoryIds ";
    private final String INITIATOR_ID_CLAUSE = "and e.initiator.id in :userIds ";
    private final String TEXT_CLAUSE = "and (e.annotation like :text or e.description like :text) ";
    private final String PAID_CLAUSE = "and e.paid = :paid ";
    private final String ONLY_AVAILABLE_CLAUSE = "and e.confirmedRequests < e.participantLimit ";
    private final String DATE_SORT = "order by e.eventDate asc ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                                  List<Long> categoryIds, String text, Boolean paid, Boolean onlyAvailable,
                                  Pageable pageable, EventsSort eventsSort) {

        String query =  BASIC_QUERY;

        if (start != null) {
            query += START_CLAUSE;

        }
        if (end != null) {
            query += END_CLAUSE;

        }
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
       query +=  DATE_SORT;

        TypedQuery<Event> typedQuery =  entityManager.createQuery(query, Event.class)
                .setParameter("states", states)
                .setParameter("start", start)
                .setParameter("end", end)
                .setParameter("userIds", userIds)
                .setParameter("categoryIds", categoryIds)
                .setParameter("text", text)
                .setParameter("paid", paid)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize());

        return typedQuery.getResultList();
    }


}
