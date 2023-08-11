package ru.practicum.main.privateController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.PublicUpdateEventDto;
import ru.practicum.main.event.service.EventService;
import ru.practicum.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
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

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public EventFullDto addEvent(@Positive @PathVariable long userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId,
                                    @Valid @RequestBody PublicUpdateEventDto updateEventDto) {
        return eventService.updateEvent(userId, eventId, updateEventDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<EventShortDto> findUserEvents(@Positive @PathVariable Long userId,
                                              @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                              @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findUserEvents(userId, PageRequest.of(from / size, size));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{eventId}")
    public EventFullDto findEventById(@Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
       return eventService.findEventById(userId, eventId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestsStatus(@Positive @PathVariable Long userId,
                                                               @Positive @PathVariable Long eventId,
                                                               @RequestBody EventRequestStatusUpdateRequest
                                                                           eventRequestStatusUpdateRequest) {

        return requestService.changeRequestsStatus(userId, eventId,
                eventRequestStatusUpdateRequest);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{eventId}/requests")
    public List<RequestDto> findRequestsByEventInitiatorId(@Positive @PathVariable Long userId,
                                                @Positive @PathVariable Long eventId) {
        return requestService.findRequestsByEventInitiatorId(userId, eventId);
    }
}
