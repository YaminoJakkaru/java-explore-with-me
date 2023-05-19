package ru.practicum.stats.hit.model;


import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;



@Accessors(chain = true)
@Entity
@Data
@Table(name = "endpoint_hit")
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 512)
    private String app;

    @Column(nullable = false, length = 2048)
    private String uri;

    @Column(nullable = false, length = 45)
    private String ip;

    @Column( nullable = false)
    @CreationTimestamp
    private LocalDateTime instant;
}
