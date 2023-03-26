package com.team.puddy.domain.question.dto.response;

import com.team.puddy.global.mapper.validator.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;


public record QuestionResponseDto (String title,
                                   String content,
                                   @Category String category,
                                   int postCategory) {
    @Builder
    public QuestionResponseDto {}
}