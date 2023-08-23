package ru.practicum.main.comment.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.comment.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto addComment(long userId, long eventId, CommentDto commentDto);

    CommentDto updateComment(long userId, long commentId, CommentDto commentDto);

    void removeComment(long userId, long commentId);

    CommentDto findCommentById(long commentId);

    List<CommentDto> findCommentsByEventId(long eventId, Pageable pageable);

}
