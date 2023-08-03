package ru.practicum.main.request.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.request.dto.RequestDto;
import ru.practicum.main.request.status.Status;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "request",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"event_id", "requester_id"})}
)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "create_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User requester;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(255) default 'PENDING'")
    private Status status;

    public RequestDto toRequestDto() {
    return  new RequestDto()
            .setId(this.getId())
            .setCreated(this.getCreated())
            .setEvent(this.getEvent().getId())
            .setRequester(this.getRequester().getId())
            .setStatus(this.getStatus());
    }
}
