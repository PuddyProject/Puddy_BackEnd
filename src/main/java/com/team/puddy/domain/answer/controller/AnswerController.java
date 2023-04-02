package com.team.puddy.domain.answer.controller;

import com.team.puddy.domain.answer.dto.RequestAnswerDto;
import com.team.puddy.domain.answer.dto.ResponseAnswerDto;
import com.team.puddy.domain.answer.service.AnswerService;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/questions/{questionId}/answers/write")
    public Response<Void> registerAnswer(@PathVariable Long questionId,
                                         @RequestBody RequestAnswerDto requestDto,
                                         @AuthenticationPrincipal JwtUserDetails user) {

        answerService.addAnswer(requestDto, user.getUserId(), questionId);
        return Response.success();
    }

    @PatchMapping("/questions/{questionId}/answers/{answerId}")
    public Response<Void> answerSelect(@PathVariable Long questionId,
                                       @PathVariable Long answerId,
                                       @AuthenticationPrincipal JwtUserDetails user) {
        answerService.answerSelect(questionId, answerId,user.getUserId());
        return Response.success();
    }

    @GetMapping("/questions/{questionId}/answers")
    public Response<List<ResponseAnswerDto>> getAnswerList(@PathVariable Long questionId) {

        List<ResponseAnswerDto> answerList = answerService.getAnswerList(questionId);

        return Response.success(answerList);
    }

    @GetMapping("/answers/count")
    public Response<Long> getAnswerCount() {
        answerService.getAnswerCount();
        return Response.success(answerService.getAnswerCount());
    }
}
