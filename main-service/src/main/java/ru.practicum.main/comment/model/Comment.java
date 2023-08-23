package ru.practicum.main.comment.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 512)
    private String note;

    @Column(name = "edited", columnDefinition = "boolean default false")
    private boolean edited;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;

    @Column(name = "create_date", nullable = false,  columnDefinition = "TIMESTAMP")
    private LocalDateTime createOn  = LocalDateTime.now();



    public CommentDto toCommentDto() {

        return new CommentDto()
                .setId(this.getId())
                .setNote(this.getNote())
                .setAuthor(this.getAuthor().toUserDto())
                .setCreateOn(this.getCreateOn())
                .setEdited(this.isEdited());
    }

}
