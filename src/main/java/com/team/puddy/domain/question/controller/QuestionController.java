package com.team.puddy.domain.question.controller;


import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.service.QuestionService;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.mapper.QuestionMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class QuestionController {

    private final S3UpdateUtil s3UpdateUtil;

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    @PostMapping(value = "/write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "QNA 게시글 등록 메서드")
    public Response<?> registerQuestion(@RequestPart(value = "request") @Valid QuestionRequestDto requestDto,
                                        @RequestParam(value = "file", required = false) MultipartFile file,
                                        @AuthenticationPrincipal JwtUserDetails user) throws IOException {
        String imagePath = "";
        if (file != null && !file.isEmpty()) {
            String fileName = s3UpdateUtil.createFileName(file.getOriginalFilename());
            imagePath = s3UpdateUtil.uploadToS3(file, fileName);
        }
        questionService.addQuestion(requestDto, imagePath, user.getUserId());

        return Response.success(imagePath);
    }


    @GetMapping("/{questionId}")
    @Operation(summary = "QNA 게시글 단건 조회 메서드")
    public Response<QuestionResponseDto> getQuestion(@PathVariable Long questionId) {
        questionService.increaseViewCount(questionId);
        QuestionResponseDto question = questionService.getQuestion(questionId);
        return Response.success(question);
    }

    @GetMapping
    @Operation(summary = "QNA 리스트 조회 메서드")
    public Response<QuestionListResponseDto> getQuestionList(
            @RequestParam int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);

        QuestionListResponseDto questionListDto = questionService.getQuestionList(pageable);

        return Response.success(questionListDto);
    }

    @GetMapping("/count")
    public Response<Long> getQuestionCount(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        return Response.success(questionService.getQuestionCount());
    }

}
