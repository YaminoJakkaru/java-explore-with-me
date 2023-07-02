package ru.practicum.main.request.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.request.status.Status;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RequestDto {

    private long id;


    private LocalDateTime createOn;


    private long event;


    private long requester;


    private Status status;
}
