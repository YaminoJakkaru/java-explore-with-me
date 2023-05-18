package ru.practicum.stats.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.stats.dto.ViewedEndpointHitDto;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import ru.practicum.stats.stats.service.StatsService;


@RestController
@RequestMapping(path = "/stats")
public final class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public List<ViewedEndpointHitDto> getStats(@RequestParam(required = false) String start,
                                               @RequestParam(required = false) String end,
                                               @RequestParam (required = false) List<String> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = start == null ?
                LocalDateTime.MIN : LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8),formatter);
        LocalDateTime endTime =  end == null ?
                LocalDateTime.MAX : LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8),formatter);
        return statsService.getStats(startTime, endTime, uris, unique);
    }
}
