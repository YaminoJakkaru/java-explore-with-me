package ru.practicum.stats.hit.mapper;

import ru.practicum.stats.dto.StoredEndpointHitDto;
import ru.practicum.stats.hit.model.EndpointHit;

public final class HitMapper {
    public static EndpointHit toEndpointHit(StoredEndpointHitDto storedEndpointHitDto) {
        return  new EndpointHit()
                .setApp(storedEndpointHitDto.getApp())
                .setIp(storedEndpointHitDto.getIp())
                .setUri(storedEndpointHitDto.getUri());
    }
}
