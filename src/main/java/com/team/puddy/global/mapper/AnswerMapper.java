package com.team.puddy.global.mapper;

import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.answer.dto.RequestAnswerDto;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AnswerMapper {

    default Answer toEntity(RequestAnswerDto answerDto, User user, Question question) {
        return Answer.builder()
                .content(answerDto.content())
                .postCategory(answerDto.postCategory())
                .selected(false)
                .user(user)
                .question(question).build();
    }
}
