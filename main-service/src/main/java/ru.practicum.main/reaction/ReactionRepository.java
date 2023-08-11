package ru.practicum.main.reaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.reaction.model.Reaction;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Long> {

    int removeReactionByCommentIdAndResponderId(long commentId, long userId);

    List<Reaction> findReactionByCommentId(long commentId);


    List<Reaction> findReactionsByCommentIdIn(List<Long> commentIds);
}
