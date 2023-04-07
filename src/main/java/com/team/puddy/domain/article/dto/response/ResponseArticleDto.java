package com.team.puddy.domain.article.dto.response;

import com.team.puddy.domain.answer.dto.ResponseAnswerDto;
import com.team.puddy.domain.article.domain.ArticleTag;
import com.team.puddy.domain.comment.dto.response.ResponseCommentDto;
import com.team.puddy.global.mapper.validator.Category;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseArticleDto(Long articleId,
                                 String title,
                                 String content,
                                 String nickname,
                                 LocalDateTime createdDate,
                                 List<String> images,
                                 long viewCount,
                                 long likeCount,
                                 int postCategory,
                                 List<ArticleTag> tagList,
                                 List<ResponseCommentDto> commentList) {
}
