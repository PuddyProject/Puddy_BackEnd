package com.team.puddy.domain.expert.entity;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ExpertTest {

    @Autowired
    ExpertRepository expertRepository;

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
//    @WithMockUser(username = "kimGeonWoo")
    public void auditingTest(){
        Expert newExpert = new Expert();
        newExpert.setIntroduce("저는 홍길동입니다.");
        newExpert.setCareer("1930년 홍길동병원대표");
        newExpert.setLocation("서울시 마포구 합정동");
        newExpert.setEducation("서울대학교 수의학");
        expertRepository.save(newExpert);

        em.flush();
        em.clear();


        Expert expert = expertRepository.findById(newExpert.getId())
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("register time : " + expert.getCreatedDate());
        System.out.println("update time : " + expert.getModifiedDate());
    }

}