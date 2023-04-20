package com.team.puddy.domain.article.dto.response;

import lombok.Builder;

import java.util.List;

public record ResponseArticleListDto (
        List<ResponseArticleExcludeCommentDto> articleList,
        boolean hasNextPage
) {
    @Builder
    public ResponseArticleListDto {
    }
}
