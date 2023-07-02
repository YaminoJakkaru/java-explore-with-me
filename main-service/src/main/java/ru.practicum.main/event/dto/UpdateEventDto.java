package ru.practicum.main.event.dto;


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

    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

    private String title;
}
