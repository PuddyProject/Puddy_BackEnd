package com.team.puddy.global.mapper;

import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QuestionMapper {


    default Question toEntity(QuestionRequestDto requestDto, String imagePath, User user) {
        return Question.builder()
                .title(requestDto.title())
                .user(user)
                .content(requestDto.content())
                .category(requestDto.category())
                .imagePath(imagePath)
                .build();
    }


    default QuestionResponseDto toDto(Question question) {
        return QuestionResponseDto.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategory())
                .build();
    }


}
