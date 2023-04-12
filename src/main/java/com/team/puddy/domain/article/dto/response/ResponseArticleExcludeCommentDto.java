package com.team.puddy.domain.article.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.team.puddy.domain.article.domain.ArticleTag;
import com.team.puddy.domain.comment.dto.response.ResponseCommentDto;
import com.team.puddy.domain.image.domain.Image;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseArticleExcludeCommentDto(Long articleId,
                                               String title,
                                               String content,
                                               String nickname,
                                               LocalDateTime createdDate,
                                               long viewCount,
                                               long likeCount,
                                               int postCategory,
                                               String imagePath,
                                               @JsonIgnoreProperties({"article"})
                                               List<ArticleTag> tagList) {

    @Builder
    public ResponseArticleExcludeCommentDto {
    }
}
