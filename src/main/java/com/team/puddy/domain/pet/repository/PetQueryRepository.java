package com.team.puddy.domain.pet.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.pet.domain.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.team.puddy.domain.pet.domain.QPet.pet;
import static com.team.puddy.domain.user.domain.QUser.user;


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

    public Optional<Pet> findPetByUserId(Long userId) {
        return Optional.ofNullable(queryFactory.selectFrom(pet)
                .join(pet.user, user)
                .where(user.id.eq(userId))
                .fetchOne());
    }
}