package ru.practicum.stats.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Data
public final class StoredEndpointHitDto {

    private String app;

    private String uri;

    private String ip;

    private LocalDateTime instant;
}
