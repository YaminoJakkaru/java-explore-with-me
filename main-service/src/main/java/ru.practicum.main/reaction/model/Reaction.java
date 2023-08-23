package ru.practicum.main.reaction.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.user.model.User;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "responder_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User responder;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment comment;

    @Column(name = "positive", nullable = false)
    private Boolean positive;


}
