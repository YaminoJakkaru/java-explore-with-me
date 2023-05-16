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
public class StatsController {

    private final StatsService statsService;

    private  final String DEFAULT_ENCODED_START = "-999999999-01-01+00%3A2100%3A2100";
    private  final String DEFAULT_ENCODED_END = "999999999-12-31+00%3A2100%3A2100";
    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public List<ViewedEndpointHitDto> getStats(@RequestParam(defaultValue = DEFAULT_ENCODED_START) String start,
                                               @RequestParam(defaultValue = DEFAULT_ENCODED_END) String end,
                                               @RequestParam Optional<String[]> uris,
                                               @RequestParam(defaultValue = "false") boolean unique) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String decodeStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
        String decodeEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);
        LocalDateTime startTime = LocalDateTime.parse(decodeStart,formatter);
        LocalDateTime endTime = LocalDateTime.parse(decodeEnd,formatter);
        return statsService.getStats(startTime, endTime, uris, unique);
    }
}
