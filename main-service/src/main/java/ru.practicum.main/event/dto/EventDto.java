package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.state.State;
import ru.practicum.main.user.dto.UserDto;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class EventDto {
    private long id;

    private String annotation;

    private CategoryDto category;

    private long confirmedRequests;

    private LocalDateTime createdOn;

    String description;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private UserDto initiator;

    private Location location;

    private boolean paid;

    private long participantLimit;

    private LocalDateTime publishedOn;

    private boolean requestModeration;

    private State state;

    private String title;

    private long views;
}
