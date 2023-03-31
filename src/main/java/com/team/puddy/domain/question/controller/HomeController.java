package com.team.puddy.domain.question.controller;


import com.team.puddy.domain.question.dto.response.MainPageResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponeDtoExcludeAnswer;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.service.QuestionService;
import com.team.puddy.global.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class HomeController {

    private final QuestionService questionService;

    @GetMapping
    @Operation(summary = "메인 페이지 데이터 조회 메서드")
    public Response<MainPageResponseDto> mainPage() {
        List<QuestionResponeDtoExcludeAnswer> popularQuestions = questionService.getPopularQuestions();
        List<QuestionResponeDtoExcludeAnswer> recentQuestions = questionService.getRecentQuestions();

        //TODO: 그 외의 요청정보 추가시 수정
        MainPageResponseDto mainPageData = MainPageResponseDto.builder()
                .recentQuestions(recentQuestions)
                .popularQuestions(popularQuestions)
                .build();

        return Response.success(mainPageData);
    }
}

