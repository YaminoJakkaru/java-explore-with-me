package ru.practicum.main.publicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event.sort.EventsSort;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
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
                                                   @Positive @RequestParam(defaultValue = "10") Integer size,
                                                   HttpServletRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = rangeStart == null ?
                null : LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8),
                formatter);
        LocalDateTime endTime = rangeEnd == null ?
                null : LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8), formatter);
        if (startTime == null && endTime == null) {
            startTime = LocalDateTime.now();
        }

        if (endTime != null && endTime.isBefore(startTime)) {
            throw new ValidationException("rangeStart cannot be later than rangeEnd");
        }
        return eventService.findPublishedEvents(startTime, endTime, categories, text, paid, onlyAvailable,
                sort, PageRequest.of(from / size, size), request);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{id}")
    public EventFullDto findEventById(@Positive @PathVariable long id, HttpServletRequest request) {
        return eventService.findEventById(id, request);
    }
}
