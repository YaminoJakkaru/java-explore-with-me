package ru.practicum.main.request.service;

import ru.practicum.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.status.Status;

import java.util.List;

public interface RequestService {

    RequestDto addRequest(long userId, long eventId);
    EventRequestStatusUpdateResult changeRequestsStatus(long userId, long eventId, List<Long> requestsIds,
                                                        Status status);
    RequestDto cancelRequestsStatus(long userId, long requestsIds);

    List<RequestDto> findRequestsByEventInitiatorId(long userId, long eventId);

    List<RequestDto> findRequestsByRequesterId(long userId);
}
