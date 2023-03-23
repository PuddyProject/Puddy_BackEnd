package com.team.puddy.domain.expert.repository;

import com.team.puddy.domain.expert.domain.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Expert findByCompanyNm(String companyNm);
}
