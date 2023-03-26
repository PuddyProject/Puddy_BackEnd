package com.team.puddy.domain.answer.repository;

import com.team.puddy.domain.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
}
