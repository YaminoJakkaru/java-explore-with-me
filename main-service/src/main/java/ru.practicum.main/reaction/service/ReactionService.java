package ru.practicum.main.reaction.service;


public interface ReactionService {

    void addReaction(long commentId, long userId, boolean positive);

    void removeReaction(long commentId, long userId);
}
