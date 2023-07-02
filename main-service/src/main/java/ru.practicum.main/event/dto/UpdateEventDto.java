package ru.practicum.main.event.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.event.state.StateAction;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UpdateEventDto {

    private String annotation;

    private Long categoryId;

    private String description;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    private String title;
}
