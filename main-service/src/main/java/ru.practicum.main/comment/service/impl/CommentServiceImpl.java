package ru.practicum.main.comment.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.CommentRepository;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.reaction.ReactionRepository;
import ru.practicum.main.reaction.model.Reaction;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.UserRepository;
import ru.practicum.main.user.model.User;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ReactionRepository reactionRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.commentRepository = commentRepository;
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public CommentDto addComment(long userId, long eventId, CommentDto commentDto) {
       User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + " was not found");
        }
        Event event = eventRepository.findEventById(eventId);
        if (event == null) {
            throw new DataValidationException("Event with id=" + eventId + " was not found");
        }
        Comment comment = commentRepository.save(commentDto.toComment()
                .setEvent(event)
                .setAuthor(user));
        log.info("User " + userId + " added comment " + comment.getId() + " to event " + eventId);
        return comment.toCommentDto();
    }

    @Transactional
    @Override
    public CommentDto updateComment(long userId, long commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findCommentById(commentId);
        if (comment == null || comment.getAuthor().getId() != userId) {
            throw new NotFoundException("a comment " + commentId + " belonging to a user " + userId
                    + " was not found");
        }
        log.info("User " + userId + " update comment " + commentId);
        comment.setNote(commentDto.getNote());
        comment.setEdited(true);
        return commentRepository.save(comment).toCommentDto();
    }

    @Transactional
    @Override
    public void removeComment(long userId, long commentId) {
        int response = commentRepository.removeCommentByIdAndAuthorId(commentId, userId);
        if(response == 0) {
            throw new NotFoundException("Comment from user " + userId + " with id = " + commentId + " not found");
        }
        log.info("User " + userId + " deleted comment " + commentId);
    }

    @Override
    public CommentDto findCommentById(long commentId) {
        Comment comment = commentRepository.findCommentById(commentId);
        if (comment == null) {
            throw new NotFoundException("Comment with id=" + commentId + " was not found");
        }
        CommentDto commentDto = comment.toCommentDto();
        List<Reaction> reactions = reactionRepository.findReactionByCommentId(commentId);
        commentDto.setLikes(reactions.stream().filter(Reaction::getPositive).count());
        commentDto.setDislikes(reactions.size() - commentDto.getLikes());
        return commentDto;
    }

    @Override
    public List<CommentDto> findCommentsByEventId(long eventId, Pageable pageable) {
        List<CommentDto> comments = commentRepository.findCommentsByEventIdOrderByIdDesc(eventId, pageable).stream()
                .map(Comment::toCommentDto).collect(Collectors.toList());
        List<Reaction> reactions = reactionRepository.findReactionsByCommentIdIn(comments.stream()
                .map(CommentDto::getId).collect(Collectors.toList()));
        long reactionsSize = reactions.size();
        for (CommentDto commentDto : comments) {
            reactions.removeIf(reaction -> reaction.getId() == commentDto.getId() && reaction.getPositive());
            commentDto.setLikes(reactionsSize - reactions.size());
            reactionsSize -= commentDto.getLikes();
            reactions.removeIf(reaction -> reaction.getId() == commentDto.getId());
            commentDto.setDislikes(reactionsSize - reactions.size());
            reactionsSize -= commentDto.getDislikes();
        }
        return comments;
    }

}
