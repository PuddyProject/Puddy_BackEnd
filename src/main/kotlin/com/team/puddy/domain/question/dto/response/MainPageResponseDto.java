package com.team.puddy.domain.question.dto.response;

import com.team.puddy.domain.article.dto.response.ResponseArticleExcludeCommentDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import lombok.Builder;

import java.util.List;

public record MainPageResponseDto(List<ResponseQuestionExcludeAnswerDto> popularQuestions,
                                  List<ResponseQuestionExcludeAnswerDto> recentQuestions,
                                  List<ResponseExpertDto> recentExperts,
                                  List<ResponseArticleExcludeCommentDto> recentArticles,
                                  List<ResponseArticleExcludeCommentDto> popularArticles,
                                  boolean hasPet) {
    @Builder
    public MainPageResponseDto {
    }
}
