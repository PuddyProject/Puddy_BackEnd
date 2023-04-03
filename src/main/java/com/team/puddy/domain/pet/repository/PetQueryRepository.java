package com.team.puddy.domain.pet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.team.puddy.domain.pet.domain.QPet.pet;


@Repository
@RequiredArgsConstructor
public class PetQueryRepository {

    private final JPAQueryFactory queryFactory;

    public boolean existsPetByUserId(Long userId) {
        return queryFactory
                .selectOne()
                .from(pet)
                .where(pet.user.id.eq(userId))
                .fetchFirst() != null;
    }

}