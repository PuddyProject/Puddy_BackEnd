package com.team.puddy.domain.question.dto.response;

import com.team.puddy.global.mapper.validator.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;


public record QuestionResponseDto (String title,
                                   String content,
                                   String nickname,
                                   @Category String category,
                                   long viewCount,
                                   boolean isSolved,
                                   int postCategory) {
    @Builder
    public QuestionResponseDto {}
}