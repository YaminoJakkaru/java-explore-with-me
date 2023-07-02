package ru.practicum.main.event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.event.model.Event;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class NewEventDto {

    @NotBlank
    private String annotation;

    @NotNull
    private long categoryId;

    @NotBlank
    private String description;

    @NotBlank
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    @NotNull
    private boolean paid;

    @NotNull
    private long participantLimit;

    @NotNull
    private boolean requestModeration;

    @NotBlank
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
