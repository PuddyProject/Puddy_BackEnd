package com.team.puddy.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime


@EntityListeners(value = [AuditingEntityListener::class])
@MappedSuperclass
abstract class BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @CreatedDate
    @Column(updatable = false)
    private val createdAt: LocalDateTime? = null

    @LastModifiedDate
    private val updatedAt: LocalDateTime? = null
}
