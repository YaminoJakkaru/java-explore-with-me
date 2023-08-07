package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.aspectj.lang.annotation.After;
import ru.practicum.main.event.model.Event;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class  NewEventDto {

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotNull
    private long category;

    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @NotNull
    private boolean paid;

    @NotNull
    private long participantLimit;

    @NotNull
    private boolean requestModeration = true;

    @NotBlank
    @Size(min = 3, max = 120)
    private String title;

    public Event toEvent() {
        return new Event()
                .setAnnotation(this.getAnnotation())
                .setDescription(this.getDescription())
                .setEventDate(this.getEventDate())
                .setLat(this.getLocation().getLat())
                .setLon(this.getLocation().getLon())
                .setPaid(this.isPaid())
                .setParticipantLimit(this.getParticipantLimit())
                .setRequestModeration(this.isRequestModeration())
                .setTitle(this.getTitle());
    }
}
