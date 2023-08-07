package ru.practicum.stats.dto.hit;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;;


@Data
@Accessors(chain = true)
public final class  ViewedEndpointHitDto {

    private  String app;

    private  String uri;

    private  long hits;

    public void addHit() {
        hits += 1;
    }
}
