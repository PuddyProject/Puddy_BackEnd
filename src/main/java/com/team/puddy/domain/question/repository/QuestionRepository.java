package com.team.puddy.domain.question.repository;

import com.team.puddy.domain.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Optional<Question> findByTitle(String title);

    Page<Question> findByOrderByModifiedDateDesc(Pageable pageable);
}
