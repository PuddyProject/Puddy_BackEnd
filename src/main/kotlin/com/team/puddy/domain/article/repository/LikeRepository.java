package com.team.puddy.domain.article.repository;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.article.domain.Likes;
import com.team.puddy.domain.article.repository.querydsl.LikeQueryRepository;
import com.team.puddy.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Likes, Long>, LikeQueryRepository {
    
    Optional<Likes> findByUserIdAndArticleId(Long userId, Long articleId);

}

