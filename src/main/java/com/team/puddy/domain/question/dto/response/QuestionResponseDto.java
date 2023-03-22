package com.team.puddy.domain.question.dto.response;

import lombok.*;

import javax.validation.constraints.NotBlank;


public record QuestionResponseDto (String title,
                                   String content,
                                   String category) {
    @Builder
    public QuestionResponseDto {}
}