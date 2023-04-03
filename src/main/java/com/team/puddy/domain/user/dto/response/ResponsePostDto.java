package com.team.puddy.domain.user.dto.response;

import com.team.puddy.domain.answer.dto.ResponseAnswerDto;
import com.team.puddy.domain.answer.dto.ResponseAnswerDtoExcludeUser;
import com.team.puddy.domain.question.dto.response.QuestionResponeDtoExcludeAnswer;
import lombok.Builder;

import java.util.List;

public record ResponsePostDto(List<QuestionResponeDtoExcludeAnswer> questionList,
                              List<ResponseAnswerDtoExcludeUser> answerList
) {

    @Builder
    public ResponsePostDto {
    }
}
