package ru.practicum.stats.stats.service;

import ru.practicum.stats.dto.ViewedEndpointHitDto;
import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ViewedEndpointHitDto> getStats(LocalDateTime start, LocalDateTime end,
                                        List<String> uris, boolean unique);
}
