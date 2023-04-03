package com.team.puddy.domain.question.dto.response;

import com.team.puddy.domain.answer.dto.ResponseAnswerDto;
import com.team.puddy.global.mapper.validator.Category;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;


public record QuestionResponseDto (Long questionId,
                                   String title,
                                   String content,
                                   String nickname,
                                   LocalDateTime createdDate,
                                   String imagePath,
                                   @Category String category,
                                   long viewCount,
                                   boolean isSolved,
                                   int postCategory,
                                   List<ResponseAnswerDto> answerList) {
    @Builder
    public QuestionResponseDto {}
}