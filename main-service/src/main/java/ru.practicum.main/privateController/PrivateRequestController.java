package ru.practicum.main.privateController;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.service.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Validated
public class PrivateRequestController {

    private final RequestService requestService;

    public PrivateRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public RequestDto addRequest(@Positive @PathVariable long userId, @Positive @RequestParam long eventId) {
        return requestService.addRequest(userId,eventId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@Positive @PathVariable long userId, @Positive @PathVariable long requestId) {
        return requestService.cancelRequestsStatus(userId, requestId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<RequestDto> findRequestsByEventInitiatorId(@Positive @PathVariable Long userId) {
        return requestService.findRequestsByRequesterId(userId);
    }
}
