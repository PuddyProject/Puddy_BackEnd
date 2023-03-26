package com.team.puddy.domain.question.repository;

import com.team.puddy.domain.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByTitle(String title);

    Page<Question> findByOrderByModifiedDateDesc(Pageable pageable);

    @Modifying
    @Query("UPDATE Question q SET q.view_count = q.view_count + 1 WHERE q.id = :questionId")
    void increaseViewCount(@Param("questionId") Long questionId);

}
