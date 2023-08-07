package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.state.State;
import ru.practicum.main.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventFullDto {
    private long id;

    private String annotation;

    private CategoryDto category;

    private long confirmedRequests;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    private String description;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private UserDto initiator;

    private Location location;

    private boolean paid;

    private long participantLimit;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private State state;

    private String title;

    private long views;
}
