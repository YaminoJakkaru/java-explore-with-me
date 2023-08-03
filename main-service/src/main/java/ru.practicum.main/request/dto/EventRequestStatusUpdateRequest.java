package ru.practicum.main.request.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.request.status.Status;

import java.util.List;

@Data
@Accessors(chain = true)
public class EventRequestStatusUpdateRequest {

    List<Long> requestIds;

    Status status;
}
