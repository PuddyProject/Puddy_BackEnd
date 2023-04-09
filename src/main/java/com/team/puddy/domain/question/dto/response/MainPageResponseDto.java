package com.team.puddy.domain.question.dto.response;

import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import lombok.Builder;

import java.util.List;

public record MainPageResponseDto(List<ResponseQuestionExcludeAnswerDto> popularQuestions,
                                      List<ResponseQuestionExcludeAnswerDto> recentQuestions,
                                  List<ResponseExpertDto> recentExperts,
                                  boolean hasPet) {
    @Builder
    public MainPageResponseDto {
    }
}
