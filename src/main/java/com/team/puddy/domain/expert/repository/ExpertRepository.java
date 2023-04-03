package com.team.puddy.domain.expert.repository;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends JpaRepository<Expert, Long> {

    boolean existsByUserId(Long id);

    Optional<Expert> findByUserId(Long userId);

    List<Expert> findTop5ByOrderByCreatedDateDesc();

}
