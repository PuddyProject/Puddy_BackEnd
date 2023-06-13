package com.team.puddy.domain.user.repository;

import com.team.puddy.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository : JpaRepository<User, Long> {

    fun findByAccount(account: String): Optional<User>

    fun findByUsernameAndEmail(username: String, email: String): Optional<User>

    fun findByAccountAndUsernameAndEmail(account: String, username: String, email: String): Optional<User>

    fun findByAccountAndEmail(sub: String, email: String): Optional<User>

    fun existsByEmail(email: String): Boolean
}
