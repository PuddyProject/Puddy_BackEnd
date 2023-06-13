package com.team.puddy.domain.user.repository

import com.team.puddy.domain.user.domain.User
import java.util.*

interface UserQueryRepository {

    fun isExistsEmail(email: String): Boolean

    fun isExistsAccount(account: String): Boolean

    //유저 정보 전문가 테이블만 조회
    fun findByIdWithExpert(userId: Long): Optional<User>
    fun findByIdWithPet(userId: Long): Optional<User>

    fun isExistsNickname(nickname: String): Boolean

    //유저 정보 펫과 전문가만 조회
    fun findByIdWithPetAndExpert(userId: Long): Optional<User>

    //유저 정보 전체 페치조인 조회
    fun findByUserId(userId: Long): Optional<User>

    fun findByAccount(account: String): Optional<User>
}