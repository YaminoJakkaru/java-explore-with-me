package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.stats.hit.model.EndpointHit;

@Repository
public interface  HitRepository extends JpaRepository<EndpointHit, Long>, CustomHitRepository{

}
