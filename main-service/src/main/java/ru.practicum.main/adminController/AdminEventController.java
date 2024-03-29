package ru.practicum.main.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.AdminUpdateEventDto;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.event.state.State;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Validated
public class AdminEventController {

    private final EventService eventService;

    @Autowired
    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable Long eventId,
                                    @Valid @RequestBody AdminUpdateEventDto updateEventDto) {
        return eventService.updateEvent(eventId, updateEventDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<EventFullDto> findEvents(@RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(required = false) List<Long> users,
                                         @RequestParam(required = false) List<State> states,
                                         @RequestParam(required = false) List<Long> categories,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = rangeStart == null ?
                null : LocalDateTime.parse(URLDecoder.decode(rangeStart, StandardCharsets.UTF_8),
                formatter);
        LocalDateTime endTime =  rangeEnd == null ?
                null : LocalDateTime.parse(URLDecoder.decode(rangeEnd, StandardCharsets.UTF_8),formatter);
        return eventService.findEvents(startTime, endTime, users,states, categories,
                PageRequest.of(from/ size, size));
    }
}
