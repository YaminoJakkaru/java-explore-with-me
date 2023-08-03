package ru.practicum.stats.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.hit.ViewedEndpointHitDto;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.practicum.stats.stats.service.StatsService;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping(path = "/stats")

public final class StatsController {


    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;

    }

    @GetMapping
    public List<ViewedEndpointHitDto> getStats(@NotNull @RequestParam String start,
                                               @NotNull @RequestParam String end,
                                               @RequestParam (required = false) List<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8),formatter);
        LocalDateTime endTime =  LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8),formatter);
        if ( endTime.isBefore(startTime)) {
            throw new ValidationException("rangeStart cannot be later than rangeEnd");
        }
        return statsService.getStats(startTime, endTime, uris, unique);
    }
}
