package com.team.puddy.domain.user.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory
import com.team.puddy.domain.expert.domain.QExpert.expert
import com.team.puddy.domain.image.domain.QImage.image
import com.team.puddy.domain.pet.domain.QPet.pet
import com.team.puddy.domain.user.domain.QUser.user
import com.team.puddy.domain.user.domain.User
import com.team.puddy.domain.user.repository.UserQueryRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class UserQueryRepositoryImpl (
    private val queryFactory : JPAQueryFactory,
) : UserQueryRepository {

     override fun isExistsEmail(email : String) :Boolean {
        return queryFactory.select(user.id)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne() != null;
    }

     override fun isExistsAccount(account : String): Boolean {
        return queryFactory.select(user.id)
                .from(user)
                .where(user.account.eq(account))
                .fetchOne() != null;
    }

    //유저 정보 전문가 테이블만 조회
    override fun findByIdWithExpert(userId : Long) : Optional<User> {

        return Optional.ofNullable(queryFactory
                .selectFrom(user)
                .leftJoin(user.image, image).fetchJoin()
                .leftJoin(user.expert, expert).fetchJoin() // INNER JOIN을 사용한 페치 조인
                .where(user.id.eq(userId))
                .fetchOne());
    }

     override fun findByIdWithPet(userId: Long): Optional<User> {
        return Optional.ofNullable(
            queryFactory
                .selectFrom(user)
                .leftJoin(user.pet, pet).fetchJoin() // INNER JOIN을 사용한 페치 조인
                .where(user.id.eq(userId))
                .fetchOne()
        )
    }

     override fun isExistsNickname(nickname: String): Boolean {
        return queryFactory.select(user.id)
            .from(user)
            .where(user.nickname.eq(nickname))
            .fetchOne() != null
    }

    // 유저 정보 펫과 전문가만 조회
    override fun findByIdWithPetAndExpert(userId: Long): Optional<User> {
        return Optional.ofNullable(
            queryFactory.selectFrom(user)
                .leftJoin(user.pet, pet).fetchJoin()
                .leftJoin(user.expert, expert).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne()
        )
    }

    // 삭제 로직 고치기
// 유저 정보 전체 페치조인 조회
    override fun findByUserId(userId: Long): Optional<User> {
        return Optional.ofNullable(
            queryFactory.selectFrom(user)
                .leftJoin(user.expert, expert).fetchJoin()
                .leftJoin(user.image, image).fetchJoin()
                .leftJoin(user.pet, pet).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne()
        )
    }

     override fun findByAccount(account: String): Optional<User> {
        return Optional.ofNullable(
            queryFactory.selectFrom(user)
                .leftJoin(user.expert, expert).fetchJoin()
                .where(user.account.eq(account))
                .fetchOne()
        )
    }


}
