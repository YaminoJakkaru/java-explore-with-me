package ru.practicum.main.event.model;


import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.event.dto.EventFullDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.Location;
import ru.practicum.main.event.state.State;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @Column(name = "confirmed_requests")
    private long confirmedRequests = 0;

    @Column(name = "create_date", nullable = false, columnDefinition = "TIMESTAMP")

    private LocalDateTime createOn = LocalDateTime.now();

    @Column(nullable = false, length = 7000)
    String description;


    @Column(name = "event_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;

    @Column(name = "location_lat", nullable = false)
    private double lat;

    @Column(name = "location_lon", nullable = false)
    private double lon;

    @Column(nullable = false)
    private boolean paid;

    @Column(name = "participant_limit", nullable = false)
    private long participantLimit;

    @Column(name = "publish_date",  columnDefinition = "TIMESTAMP")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", columnDefinition = "boolean default true")
    private boolean requestModeration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'PENDING'")
    private State state = State.PENDING;

    @Column(nullable = false, length = 120)
    private String title;


    public EventFullDto toEventFullDto() {
        return new EventFullDto()
                .setId(this.getId())
                .setAnnotation(this.getAnnotation())
                .setCategory(this.getCategory().toCategoryDto())
                .setConfirmedRequests(this.getConfirmedRequests())
                .setDescription(this.getDescription())
                .setEventDate(this.getEventDate())
                .setInitiator(this.getInitiator().toUserDto())
                .setLocation(new Location().setLat(this.getLat()).setLon(this.getLon()))
                .setPaid(this.isPaid())
                .setPublishedOn(this.getPublishedOn())
                .setCreatedOn(this.getCreateOn())
                .setParticipantLimit(this.getParticipantLimit())
                .setRequestModeration(this.isRequestModeration())
                .setState(this.getState())
                .setTitle(this.getTitle());
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
                .setTitle(this.getTitle());
    }
}
