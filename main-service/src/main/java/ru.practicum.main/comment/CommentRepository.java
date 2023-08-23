package ru.practicum.main.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.comment.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    int removeCommentByIdAndAuthorId(long id, long userId);

    Comment findCommentById(long id);

    Page<Comment> findCommentsByEventIdOrderByIdDesc(long eventId, Pageable pageable);


}
