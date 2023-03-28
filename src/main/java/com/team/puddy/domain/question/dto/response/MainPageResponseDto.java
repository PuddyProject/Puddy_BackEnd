package com.team.puddy.domain.question.dto.response;

import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import lombok.Builder;

import java.util.List;

public record MainPageResponseDto(List<QuestionResponseDto> popularQuestions,
                                      List<QuestionResponseDto> recentQuestions) {
    @Builder
    public MainPageResponseDto {
    }
}
