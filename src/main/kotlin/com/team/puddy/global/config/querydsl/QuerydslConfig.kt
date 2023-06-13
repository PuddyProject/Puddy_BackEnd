package com.team.puddy.global.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuerydslConfig {

    @PersistenceContext
    private lateinit var em: EntityManager

    @Bean
    fun init() = JPAQueryFactory(em)

}
