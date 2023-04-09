package com.team.puddy.domain.question.dto.response;

import com.team.puddy.global.mapper.validator.Category;
import lombok.Builder;

import java.time.LocalDateTime;

public record ResponseQuestionExcludeAnswerDto(Long questionId,
                                               String title,
                                               String content,
                                               String nickname,
                                               LocalDateTime createdDate,
                                               @Category String category,
                                               long viewCount,
                                               boolean isSolved,
                                               int postCategory) {

    @Builder
    public ResponseQuestionExcludeAnswerDto {
    }
}
