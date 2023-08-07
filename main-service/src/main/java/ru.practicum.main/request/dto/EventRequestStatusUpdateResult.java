package ru.practicum.main.request.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class EventRequestStatusUpdateResult {
    private List<RequestDto> confirmedRequests;

    private List<RequestDto> rejectedRequests;
}
