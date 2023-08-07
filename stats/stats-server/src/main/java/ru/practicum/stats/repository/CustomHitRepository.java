package ru.practicum.stats.repository;

import ru.practicum.stats.hit.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomHitRepository {

    List<EndpointHit> findHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}
