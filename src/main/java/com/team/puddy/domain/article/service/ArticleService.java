package com.team.puddy.domain.article.service;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.article.domain.ArticleTag;
import com.team.puddy.domain.article.domain.Tag;
import com.team.puddy.domain.article.dto.request.RequestArticleDto;
import com.team.puddy.domain.article.dto.request.UpdateArticleDto;
import com.team.puddy.domain.article.dto.response.ResponseArticleDto;
import com.team.puddy.domain.article.repository.ArticleQueryRepository;
import com.team.puddy.domain.article.repository.ArticleRepository;
import com.team.puddy.domain.article.repository.TagRepository;
import com.team.puddy.domain.comment.dto.response.ResponseCommentDto;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.service.ImageService;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.service.UserService;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.ArticleMapper;
import com.team.puddy.global.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {

    private final UserQueryRepository userQueryRepository;
    private final ArticleRepository articleRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final ArticleMapper articleMapper;
    private final CommentMapper commentMapper;
    private final ImageService imageService;
    private final TagRepository tagRepository;

    @Transactional
    public void addArticle(RequestArticleDto requestDto, List<MultipartFile> images, Long userId) {
        //작성 유저 찾기
        User findUser = userQueryRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        //이미지 저장하기
        List<Image> imageList = imageService.saveImageListToArticle(images);
        Article article = articleMapper.toEntity(requestDto, imageList, findUser);
        //태그 매핑하기
        List<ArticleTag> articleTagList = requestDto.tagList().stream().map(tagName -> tagRepository.findByTagName(tagName)
                .map(tag -> new ArticleTag(article, tag)).orElseGet(
                        () -> new ArticleTag(article, new Tag(tagName))
                )).toList();

        article.setTagList(articleTagList);

        articleRepository.save(article);


    }

    @Transactional(readOnly = true)
    public ResponseArticleDto getArticle(Long articleId) {
        Article findArticle = articleQueryRepository.findArticleWithUserById(articleId).orElseThrow(
                () -> new NotFoundException(ErrorCode.ARTICLE_NOT_FOUND)
        );

        return articleMapper.toDto(findArticle,findArticle.getCommentList().stream().map(comment ->
                commentMapper.toDto(comment,comment.getUser().getNickname())).toList());
    }

    @Transactional
    public void updateArticle(UpdateArticleDto updateDto,Long userId) {

    }
}
