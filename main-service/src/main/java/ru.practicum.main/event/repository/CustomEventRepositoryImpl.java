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
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CustomEventRepositoryImpl implements CustomEventRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Event> findEvents(LocalDateTime start, LocalDateTime end, List<Long> userIds, List<State> states,
                                  List<Long> categoryIds, String text, Boolean paid, Boolean onlyAvailable,
                                  Pageable pageable, EventsSort eventsSort) {

        CriteriaBuilder cb =  entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        cq.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(root.get("state").in(states));

        if (start != null) {
            predicates.add(cb.greaterThan(root.get("eventDate"), start));
        }
        if (end != null) {
            predicates.add(cb.lessThan(root.get("eventDate"), end));
        }
        if(userIds != null) {
            predicates.add(root.get("initiator").get("id").in(userIds));
        }
        if(categoryIds != null) {
            predicates.add(root.get("category").get("id").in(categoryIds));
        }
        if(text != null) {
            predicates.add(cb.or(cb.like(root.get("annotation"),"%" + text + "%"),
                    cb.like(root.get("description"),"%" + text + "%")));
        }
        if (paid != null) {
            predicates.add(cb.equal(root.get("paid"),paid));
        }
        if (onlyAvailable != null && onlyAvailable) {
            predicates.add(cb.le(root.get("confirmedRequests"),root.get("participantLimit")));
        }

        cq.where(predicates.toArray(Predicate[]::new));
        cq.orderBy(cb.desc(root.get("id")));
        TypedQuery<Event> tq = entityManager.createQuery(cq);
        tq.setFirstResult((int) pageable.getOffset());
        tq.setMaxResults(pageable.getPageSize());

        return tq.getResultList();
    }


}
