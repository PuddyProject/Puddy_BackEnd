package com.team.puddy.domain.question.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record RequestQuestionDto(@NotBlank String title,
                                 @NotBlank String content,
                                 @NotBlank String category,
                                 int postCategory
                                ) {
    @Builder
    public RequestQuestionDto(@NotBlank String title, @NotBlank String content, @NotBlank String category, int postCategory) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.postCategory = postCategory;
    }
}
