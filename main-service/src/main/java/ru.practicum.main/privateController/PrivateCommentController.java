package ru.practicum.main.privateController;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.reaction.service.ReactionService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@Validated
public class PrivateCommentController {

    private final CommentService commentService;
    private final ReactionService reactionService;

    @Autowired
    public PrivateCommentController(CommentService commentService, ReactionService reactionService) {
        this.commentService = commentService;
        this.reactionService = reactionService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public CommentDto addComment(@Positive @PathVariable long userId, @Positive @RequestParam long eventId,
                                  @Valid @RequestBody CommentDto commentDto) {
        return commentService.addComment(userId, eventId, commentDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PatchMapping("/{commentId}")
    public CommentDto updateComment(@Positive @PathVariable long userId, @Positive @PathVariable long commentId,
                                    @Valid @RequestBody CommentDto commentDto) {
       return commentService.updateComment(userId, commentId, commentDto);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{commentId}")
    public void removeComment(@Positive @PathVariable long userId, @Positive @PathVariable long commentId) {
        commentService.removeComment(userId,commentId);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/{commentId}/reaction")
    public void addReaction(@Positive @PathVariable long userId, @Positive @PathVariable long commentId,
                                   @RequestParam boolean positive) {
        reactionService.addReaction(commentId, userId, positive);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping ("/{commentId}/reaction")
    public void removeReaction(@Positive @PathVariable long userId, @Positive @PathVariable long commentId) {
        reactionService.removeReaction(commentId, userId);
    }
}
