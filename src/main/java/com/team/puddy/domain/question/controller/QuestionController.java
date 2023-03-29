package com.team.puddy.domain.question.controller;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.service.QuestionService;
import com.team.puddy.domain.user.domain.User;
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

    @Value("${cloud.aws.s3.bucket}")
    private String BUCKET;
    private final QuestionService questionService;
    private final AmazonS3Client amazonS3Client;
    private final QuestionMapper questionMapper;

    @PostMapping(value = "/write", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "QNA 게시글 등록 메서드")
    public Response<?> registerQuestion(@RequestPart(value = "request") @Valid QuestionRequestDto requestDto,
                                        @RequestParam(value = "file", required = false) MultipartFile file,
                                        @AuthenticationPrincipal JwtUserDetails user) throws IOException {
        String imagePath = "";
        if (file != null && !file.isEmpty()) {
            String fileName = createFileName(file.getOriginalFilename());
            imagePath = uploadToS3(file, fileName);
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


    //중복 파일명 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(fileName);
    }

    //S3에 업로드 하는 메소드
    private String uploadToS3(MultipartFile file, String fileName) throws IOException {

        ObjectMetadata objectMetaData = new ObjectMetadata();
        objectMetaData.setContentType(file.getContentType());
        objectMetaData.setContentLength(file.getSize());
        // S3에 업로드
        amazonS3Client.putObject(
                new PutObjectRequest(BUCKET, "questions/" + fileName, file.getInputStream(), objectMetaData)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );

        return amazonS3Client.getUrl(BUCKET, "questions/" + fileName).toString();
    }

}
