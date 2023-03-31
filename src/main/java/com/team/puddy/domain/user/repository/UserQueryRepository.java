package com.team.puddy.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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



}
