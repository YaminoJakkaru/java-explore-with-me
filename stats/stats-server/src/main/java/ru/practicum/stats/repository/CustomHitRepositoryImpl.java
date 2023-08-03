package ru.practicum.stats.repository;

import ru.practicum.stats.hit.model.EndpointHit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomHitRepositoryImpl implements CustomHitRepository {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<EndpointHit> findHits(LocalDateTime start, LocalDateTime end, List<String> uris) {

        CriteriaBuilder cb =  entityManager.getCriteriaBuilder();
        CriteriaQuery<EndpointHit> cq = cb.createQuery(EndpointHit.class);
        Root<EndpointHit> root = cq.from(EndpointHit.class);
        cq.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.between(root.get("instant"), start, end));
        if (uris != null) {
            predicates.add((root.get("uri").in(uris)));
        }

        cq.where(predicates.toArray(Predicate[]::new));
        TypedQuery<EndpointHit> tq = entityManager.createQuery(cq);
        return tq.getResultList();
    }
}
