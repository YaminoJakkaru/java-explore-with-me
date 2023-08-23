package ru.practicum.main.reaction.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.CommentRepository;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.reaction.ReactionRepository;
import ru.practicum.main.reaction.model.Reaction;
import ru.practicum.main.reaction.service.ReactionService;
import ru.practicum.main.exception.DataValidationException;
import ru.practicum.main.user.UserRepository;
import ru.practicum.main.user.model.User;


@Service
@Slf4j
@Transactional(readOnly = true)
public class ReactionServiceImpl implements ReactionService {

    private final CommentRepository commentRepository;

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;


    @Autowired
    public ReactionServiceImpl(CommentRepository commentRepository, ReactionRepository reactionRepository,
                               UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    @Override
    public void addReaction(long commentId, long userId, boolean positive) {
        Comment comment = commentRepository.findCommentById(commentId);
        if (comment == null) {
            throw new DataValidationException("Comment with id=" + commentId + "was not found");
        }
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new DataValidationException("User with id=" + userId + "was not found");
        }
        Reaction reaction = new Reaction()
                .setComment(comment)
                .setResponder(user)
                .setPositive(positive);
        reactionRepository.save(reaction);
        log.info("User " + userId + " add " + positive + " reaction on comment " + commentId);
    }

    @Transactional
    @Override
    public void removeReaction(long commentId, long userId) {
        int response = reactionRepository.removeReactionByCommentIdAndResponderId(commentId, userId);
        if(response == 0) {
            throw new NotFoundException("Reaction from user " + userId + "to comment " + commentId + " not found");
        }
        log.info("Remove reaction from user" + userId + "to comment " + commentId);
    }
}
