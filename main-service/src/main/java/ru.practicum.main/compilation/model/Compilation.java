package ru.practicum.main.compilation.model;


import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import ru.practicum.main.compilation.dto.CompilationDto;
import ru.practicum.main.event.model.Event;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "compilation")
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column( nullable = false)
    private  boolean pinned;

    @ManyToMany
    @JoinTable(
            name = "compilation_event",
            joinColumns = { @JoinColumn(name = "compilation_id") },
            inverseJoinColumns = { @JoinColumn(name = "event_id") }
    )
    @ToString.Exclude
    private Set<Event> events = new HashSet<>() ;

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void removeEvent(long eventId) {
        this.events.removeIf(event -> event.getId() == (eventId));
    }

    public CompilationDto toCompilationDto() {
        return new CompilationDto()
                .setId(this.getId())
                .setPinned(this.isPinned())
                .setTitle(this.getTitle())
                .setEvents(this.getEvents().stream().map(Event::toEventShortDto).collect(Collectors.toSet()));
    }
}
