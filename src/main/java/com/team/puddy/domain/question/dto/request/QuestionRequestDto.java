package com.team.puddy.domain.question.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;


public record QuestionRequestDto (
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String category,
        int postCategory
) {

    @Builder
    public QuestionRequestDto {}

}
