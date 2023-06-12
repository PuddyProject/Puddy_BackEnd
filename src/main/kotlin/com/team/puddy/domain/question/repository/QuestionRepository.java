package com.team.puddy.domain.question.repository;

import com.team.puddy.domain.question.repository.querydsl.QuestionQueryRepository;
import com.team.puddy.domain.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionQueryRepository {

    @Modifying
    @Query("UPDATE Question q SET q.viewCount = q.viewCount + 1 WHERE q.id = :questionId")
    void increaseViewCount(@Param("questionId") Long questionId);

    @Modifying
    @Query("UPDATE Question q SET q.isSolved = 1 where q.id = :questionId")
    void select(@Param("questionId") Long questionId);
}