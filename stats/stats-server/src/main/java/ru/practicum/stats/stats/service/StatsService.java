package ru.practicum.stats.stats.service;

import ru.practicum.stats.dto.ViewedEndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface StatsService {
    List<ViewedEndpointHitDto> getStats(LocalDateTime start, LocalDateTime end,
                                        Optional<String[]> uris, boolean unique);
}
