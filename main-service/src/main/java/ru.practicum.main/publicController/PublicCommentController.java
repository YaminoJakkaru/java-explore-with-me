package ru.practicum.main.publicController;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.reaction.ReactionRepository;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/comments")
@Validated
public class PublicCommentController {

    private final CommentService commentService;



    public PublicCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{commentId}")
    public CommentDto findCommentById(@Positive @PathVariable long commentId) {
        return commentService.findCommentById(commentId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping
    public List<CommentDto> findCommentsByEventId(@Positive @RequestParam Long eventId,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(defaultValue = "10") Integer size) {
        return commentService.findCommentsByEventId(eventId,  PageRequest.of(from / size, size));
    }
}
