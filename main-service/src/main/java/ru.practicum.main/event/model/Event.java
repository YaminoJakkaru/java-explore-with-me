package ru.practicum.main.event.model;


import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.Location;
import ru.practicum.main.event.state.State;
import ru.practicum.main.request.status.Status;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Column(columnDefinition = "bigint default 0")
    private long confirmedRequests;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createOn;

    @Column(nullable = false, length = 520)
    String description;

    @CreationTimestamp
    @Column(name = "event_date", nullable = false)

    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;

    @Column(name = "location_lat", nullable = false)
    private double lat;

    @Column(name = "location_lon", nullable = false)
    private double lon;

    @Column(nullable = false, length = 520)
    private boolean paid;

    @Column(name = "participant_limit", nullable = false)
    private long participantLimit;

    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", columnDefinition = "boolean default true")
    private boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'PENDING'")
    private State state;

    @Column(nullable = false, length = 520)
    private String title;

    @Column(columnDefinition = "bigint default 0")
    private long views;

    public EventDto toEventDto() {
        return new EventDto()
                .setId(this.getId())
                .setAnnotation(this.getAnnotation())
                .setCategory(this.getCategory().toCategoryDto())
                .setConfirmedRequests(this.getConfirmedRequests())
                .setDescription(this.getDescription())
                .setEventDate(this.getEventDate())
                .setInitiator(this.getInitiator().toUserDto())
                .setLocation(new Location().setLat(this.getLat()).setLon(this.getLon()))
                .setPaid(this.isPaid())
                .setCreatedOn(this.getCreateOn())
                .setParticipantLimit(this.getParticipantLimit())
                .setRequestModeration(this.isRequestModeration())
                .setState(this.getState())
                .setTitle(this.getTitle())
                .setViews(this.getViews());
    }

    public EventShortDto toEventShortDto() {
        return new EventShortDto()
                .setId(this.getId())
                .setAnnotation(this.getAnnotation())
                .setCategory(this.getCategory().toCategoryDto())
                .setConfirmedRequests(this.getConfirmedRequests())
                .setEventDate(this.getEventDate())
                .setInitiator(this.getInitiator().toUserDto())
                .setPaid(this.isPaid())
                .setTitle(this.getTitle())
                .setViews(this.getViews());
    }

}
