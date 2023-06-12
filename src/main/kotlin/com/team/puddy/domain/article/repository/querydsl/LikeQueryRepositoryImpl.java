package com.team.puddy.domain.article.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.article.domain.Likes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team.puddy.domain.article.domain.QLikes.likes;

@Repository
@RequiredArgsConstructor
public class LikeQueryRepositoryImpl implements LikeQueryRepository {

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Likes> findByUserIdAndArticleId(Long userId, Long articleId) {
        return Optional.ofNullable(queryFactory.selectFrom(likes).distinct()
                        .leftJoin(likes.article).fetchJoin()
                .where(likes.userId.eq(userId), likes.article.id.eq(articleId)).fetchOne());
    }

    @Override
    public Long countByArticleId(long articleId) {
        return queryFactory.select(likes.count())
                .from(likes).distinct()
                .where(likes.article.id.eq(articleId)).fetchOne();
    }
}
