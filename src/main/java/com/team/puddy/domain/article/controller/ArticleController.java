package com.team.puddy.domain.article.controller;

import com.team.puddy.domain.article.dto.response.ResponseArticleDto;
import com.team.puddy.domain.article.service.ArticleService;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;


    @PostMapping("/write")
    public void addArticle(@RequestPart("request") RequestQuestionDto requestDto,
                                         @RequestPart(value = "images",required = false) List<MultipartFile> images,
                                         @AuthenticationPrincipal JwtUserDetails user) {
        articleService.addArticle(requestDto,images,user.getUserId());

        //TODO
    }
}
