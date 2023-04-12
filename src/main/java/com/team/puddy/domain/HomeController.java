package com.team.puddy.domain;


import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.expert.service.ExpertService;
import com.team.puddy.domain.question.dto.response.MainPageResponseDto;
import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;
import com.team.puddy.domain.question.service.QuestionService;
import com.team.puddy.domain.user.service.UserService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final UserService userService;

    private final ExpertService expertService;

    @GetMapping
    @Operation(summary = "메인 페이지 데이터 조회 메서드")
    public Response<MainPageResponseDto> mainPage(@AuthenticationPrincipal JwtUserDetails user) {
        boolean hasPet = false;
        if (user != null) {
            hasPet = userService.checkHasPet(user.getUserId());
        }
        List<ResponseExpertDto> expertList = expertService.getRecentExperts();
        List<ResponseQuestionExcludeAnswerDto> popularQuestions = questionService.getPopularQuestions();
        List<ResponseQuestionExcludeAnswerDto> recentQuestions = questionService.getRecentQuestions();

        MainPageResponseDto mainPageData = MainPageResponseDto.builder()
                .recentQuestions(recentQuestions)
                .popularQuestions(popularQuestions)
                .recentExperts(expertList)
                .hasPet(hasPet)
                .build();

        return Response.success(mainPageData);
    }
}

