package ru.practicum.main.event.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventShortDto {

    private long id;

    private String annotation;

    private CategoryDto category;

    private long confirmedRequests;

    private LocalDateTime eventDate;

    private UserDto initiator;

    private boolean paid;

    private String title;

    private long views;
}
