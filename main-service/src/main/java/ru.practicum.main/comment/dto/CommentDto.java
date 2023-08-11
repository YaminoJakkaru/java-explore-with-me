package ru.practicum.main.comment.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.user.dto.UserDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class CommentDto {

    private long id;

    @NotBlank
    @Size(min = 1, max = 512)
    private String note;

    private boolean edited;

    private UserDto author;

    private LocalDateTime createOn;

    private long likes;

    private long dislikes;

    public Comment toComment () {
        return new Comment()
                .setNote(this.getNote());
    }
}
