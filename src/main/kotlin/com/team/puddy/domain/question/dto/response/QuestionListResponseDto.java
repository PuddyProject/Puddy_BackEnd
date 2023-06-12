package com.team.puddy.domain.question.dto.response;

import lombok.Builder;

import java.util.List;

public record QuestionListResponseDto(
        List<ResponseQuestionExcludeAnswerDto> questionList,
        boolean hasNextPage
) {
    @Builder
    public QuestionListResponseDto {
    }
}
