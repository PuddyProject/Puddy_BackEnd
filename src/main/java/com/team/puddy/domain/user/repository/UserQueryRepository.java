package com.team.puddy.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team.puddy.domain.expert.domain.QExpert.expert;
import static com.team.puddy.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean isExistsEmail(String email) {
        return queryFactory.select(user.id)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne() != null;
    }

    public boolean isExistsAccount(String account) {
        return queryFactory.select(user.id)
                .from(user)
                .where(user.account.eq(account))
                .fetchOne() != null;
    }

    public Optional<User> findUserWithExpertByUserId(Long userId) {

        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .innerJoin(user.expert, expert).fetchJoin() // INNER JOIN을 사용한 페치 조인
                .where(user.id.eq(userId))
                .fetchOne());

    }

}
