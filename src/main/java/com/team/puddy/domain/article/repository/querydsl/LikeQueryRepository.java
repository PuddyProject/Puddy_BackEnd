package com.team.puddy.domain.article.repository.querydsl;

import com.team.puddy.domain.article.domain.Likes;

import java.util.Optional;

public interface LikeQueryRepository {

    Optional<Likes> findByUserIdAndArticleId(Long userId, Long articleId);

    Long countByArticleId(long articleId);
}
