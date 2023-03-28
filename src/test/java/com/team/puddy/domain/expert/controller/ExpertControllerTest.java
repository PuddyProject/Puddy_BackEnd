package com.team.puddy.domain.expert.controller;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
public class ExpertControllerTest {

    @Autowired
    ExpertRepository expertRepository;

    @Test
    @DisplayName("전문가 저장 테스트")
    public void createExpertTest() {

        Expert expert = new Expert();
        expert.setCompanyNm("사업가 번호 : 16516-5416516");
        expert.setIntroduce("저는 홍길동입니다.");
        expert.setCompanyName("홍길동병원");
        expert.setCareer("1930년 홍길동병원대표");
        expert.setLocation("서울시 마포구 합정동");
        Expert savedExpert = expertRepository.save(expert);
        assertEquals(expert , savedExpert);

    }
}
