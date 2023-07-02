package ru.practicum.main.privateController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.service.RequestService;
import ru.practicum.main.request.status.Status;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@RequestMapping(path = "/users/{userId}/events")
@Validated
public class PrivateEventController {

    private final EventService eventService;

    private final RequestService requestService;

    @Autowired
    public PrivateEventController(EventService eventService, RequestService requestService) {
        this.eventService = eventService;
        this.requestService = requestService;
    }

    @PostMapping
    public EventDto addEvent(@Positive @PathVariable long userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEvent(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId,
                                @RequestBody UpdateEventDto updateEventDto) {
        return eventService.updateEvent(userId, eventId, updateEventDto);
    }

    @GetMapping
    public List<EventShortDto> findUserEvents(@Positive @PathVariable Long userId,
                                              @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                              @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findUserEvents(userId, PageRequest.of(from > 0 ? from / size : 0, size));
    }

    @GetMapping("/{eventId}")
    public EventDto findEventById(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
       return eventService.findEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestsStatus(@Positive @PathVariable Long userId,
                                                               @Positive @PathVariable Long eventId,
                                                               @RequestBody List<Long> requestIds,
                                                               @RequestBody Status status) {
        return requestService.changeRequestsStatus(userId, eventId, requestIds, status);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> findRequestsByEventInitiatorId(@Positive @PathVariable Long userId,
                                                @Positive @PathVariable Long eventId) {
        return requestService.findRequestsByEventInitiatorId(userId, eventId);
    }
}
