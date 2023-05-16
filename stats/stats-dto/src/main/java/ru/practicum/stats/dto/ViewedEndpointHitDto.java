package ru.practicum.stats.dto;


import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ViewedEndpointHitDto {
    private  String app;
    private String uri;
    private long hits;
    public ViewedEndpointHitDto(String app, String uri, long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }


}
