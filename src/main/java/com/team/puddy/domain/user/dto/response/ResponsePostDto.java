package com.team.puddy.domain.user.dto.response;

import com.team.puddy.domain.answer.dto.ResponseAnswerDtoExcludeUser;
import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;
import lombok.Builder;

import java.util.List;

public record ResponsePostDto(List<ResponseQuestionExcludeAnswerDto> questionList,
                              List<ResponseAnswerDtoExcludeUser> answerList
) {

    @Builder
    public ResponsePostDto {
    }
}
