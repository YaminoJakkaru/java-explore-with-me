package ru.practicum.stats.dto;

import lombok.Data;;


@Data
public final class  ViewedEndpointHitDto {
    private final String app;
    private final String uri;
    private final long hits;
}
