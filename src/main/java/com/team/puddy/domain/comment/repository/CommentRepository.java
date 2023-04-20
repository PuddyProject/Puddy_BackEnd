package com.team.puddy.domain.comment.repository;

import com.team.puddy.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndArticleIdAndUserId(Long commentId, Long articleId, Long userId);

    boolean existsByIdAndArticleIdAndUserId(Long commentId, Long articleId, Long userId);
}
