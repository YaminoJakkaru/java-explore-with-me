package ru.practicum.stats.hit;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.stats.hit.model.EndpointHit;

public interface HitRepository extends JpaRepository<EndpointHit, Long> {
}
