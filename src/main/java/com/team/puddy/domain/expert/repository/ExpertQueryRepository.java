package com.team.puddy.domain.expert.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.expert.domain.Expert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team.puddy.domain.expert.domain.QExpert.expert;
import static com.team.puddy.domain.review.domain.QReview.review;
import static com.team.puddy.domain.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class ExpertQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Expert> findExpertById(Long expertId) {
        return Optional.ofNullable(queryFactory.selectFrom(expert)
                .leftJoin(expert.reviewList, review).fetchJoin()
                .where(expert.id.eq(expertId))
                .fetchFirst());
    }
}
