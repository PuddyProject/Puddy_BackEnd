package com.team.puddy.domain.question.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.global.mapper.validator.Category;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseQuestionExcludeAnswerDto(Long questionId,
                                               String title,
                                               String content,
                                               String nickname,
                                               LocalDateTime createdDate,
                                               @Category String category,
                                               long viewCount,
                                               boolean isSolved,
                                               List<Image> imageList,
                                               int postCategory) {

    @Builder
    public ResponseQuestionExcludeAnswerDto {
    }
}
