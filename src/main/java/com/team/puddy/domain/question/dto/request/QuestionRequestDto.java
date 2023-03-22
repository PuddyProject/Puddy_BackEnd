package com.team.puddy.domain.question.dto.request;

import com.team.puddy.domain.question.domain.Question;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;


public record QuestionRequestDto (
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String category
) {

    @Builder
    public QuestionRequestDto {}

}
