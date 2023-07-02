package ru.practicum.main.publicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event.sort.EventsSort;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Validated
public class PublicEventController {

    private final EventService eventService;

    @Autowired
    public PublicEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<EventShortDto> findPublishedEvents(@RequestParam(required = false) String rangeStart,
                                                   @RequestParam(required = false) String rangeEnd,
                                                   @RequestParam(required = false) List<Long> categories,
                                                   @RequestParam(required = false) String text,
                                                   @RequestParam(required = false) Boolean paid,
                                                   @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                   @RequestParam(defaultValue = "EVENT_DATE") EventsSort sort,
                                                   @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                   @Positive @RequestParam(defaultValue = "10") Integer size) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = rangeStart == null ?
                LocalDateTime.MIN : LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8),
                formatter);
        LocalDateTime endTime =  rangeEnd == null ?
                LocalDateTime.MAX : LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8),formatter);
        return eventService.findPublishedEvents(startTime, endTime, categories, text, paid, onlyAvailable,
                sort, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public EventDto findEventById(@Positive @PathVariable long id) {
        return eventService.findEventById(id);
    }
}
