package com.team.puddy.domain.article.controller;

import com.team.puddy.domain.article.dto.request.RequestArticleDto;
import com.team.puddy.domain.article.dto.request.UpdateArticleDto;
import com.team.puddy.domain.article.dto.response.ResponseArticleDto;
import com.team.puddy.domain.article.dto.response.ResponseArticleListDto;
import com.team.puddy.domain.article.service.ArticleService;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.global.common.dto.Response;
import com.team.puddy.global.config.auth.JwtUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;


    @PostMapping
    public Response<Void> addArticle(@RequestPart("request") RequestArticleDto requestDto,
                                     @RequestPart(value = "images", required = false) List<MultipartFile> images,
                                     @AuthenticationPrincipal JwtUserDetails user) {
        articleService.addArticle(requestDto, images, user.getUserId());
        return Response.success();
    }

    @GetMapping("/{articleId}")
    public Response<ResponseArticleDto> getArticle(@PathVariable("articleId") Long articleId) {

        ResponseArticleDto findArticle = articleService.getArticle(articleId);

        return Response.success(findArticle);
    }

    @GetMapping
    public Response<ResponseArticleListDto> getArticleList(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        ResponseArticleListDto articleList = articleService.getArticleList(pageable);

        return Response.success(articleList);

    }

    @PutMapping("/{articleId}")
    public Response<Void> updateArticle(@PathVariable("articleId") Long articleId,
                                        @RequestPart("request") UpdateArticleDto updateDto,
                                        @RequestPart(value = "images",required = false) List<MultipartFile> images,
                                        @AuthenticationPrincipal JwtUserDetails user) {

        //TODO
        return Response.success();

    }
}
