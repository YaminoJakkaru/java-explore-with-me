package ru.practicum.main.event.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.stats.dto.hit.ViewedEndpointHitDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.stats.client.stats.StatsClient;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
public class EventStatsClient {

    private final LocalDateTime DEFAULT_START;
    private final ObjectMapper objectMapper;

    private final StatsClient statsClient;

    @Autowired
    public EventStatsClient (ObjectMapper objectMapper, StatsClient statsClient) {
        DEFAULT_START = LocalDateTime.now();

        this.objectMapper = objectMapper;

        this.statsClient = statsClient;
    }


    @SneakyThrows
    public void setViews(EventShortDto eventShortDto) {
        ResponseEntity<List<ViewedEndpointHitDto>> responseEntity =  getResponse(eventShortDto.getId());
        ViewedEndpointHitDto[] viewedEndpointHitDtos =  objectMapper.convertValue(responseEntity.getBody(),
                ViewedEndpointHitDto[].class);

        if(viewedEndpointHitDtos.length == 0) {
            return;
        }
        eventShortDto.setViews(viewedEndpointHitDtos[0].getHits());

    }

    @SneakyThrows
    public void setViews(EventFullDto eventFullDto) {
        ResponseEntity<List<ViewedEndpointHitDto>> responseEntity =  getResponse(eventFullDto.getId());
            ViewedEndpointHitDto[] viewedEndpointHitDtos =  objectMapper.convertValue(responseEntity.getBody(),
                    ViewedEndpointHitDto[].class);
            if(viewedEndpointHitDtos.length == 0) {
                return;
            }
        eventFullDto.setViews(viewedEndpointHitDtos[0].getHits());
    }

    private ResponseEntity<List<ViewedEndpointHitDto>> getResponse(long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String start = DEFAULT_START.format(formatter);
        String end = LocalDateTime.now().plusHours(1).format(formatter);
        return statsClient
                .get("?uris={uris}&start={start}&end={end}&unique=true",
                        Map.of("uris", "/events/" + id,"start", start, "end", end));

    }
}

