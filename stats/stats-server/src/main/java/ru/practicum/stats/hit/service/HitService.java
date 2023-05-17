package ru.practicum.stats.hit.service;

import ru.practicum.stats.dto.StoredEndpointHitDto;

public interface HitService {
    void addEndpointHit(StoredEndpointHitDto storedEndpointHitDto);
}
