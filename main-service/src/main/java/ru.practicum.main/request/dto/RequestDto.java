package ru.practicum.main.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.request.status.Status;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RequestDto {

    private long id;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;


    private long event;


    private long requester;


    private Status status;
}
