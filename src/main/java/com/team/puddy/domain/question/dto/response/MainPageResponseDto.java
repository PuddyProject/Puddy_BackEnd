package com.team.puddy.domain.question.dto.response;

import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import lombok.Builder;

import java.util.List;

public record MainPageResponseDto(List<QuestionResponeDtoExcludeAnswer> popularQuestions,
                                      List<QuestionResponeDtoExcludeAnswer> recentQuestions) {
    @Builder
    public MainPageResponseDto {
    }
}
