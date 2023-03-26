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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user",source = "user")
    @Mapping(target = "imagePath",source = "imagePath")
    Question toEntity(QuestionRequestDto requestDto, String imagePath, User user);


    default QuestionResponseDto toDto(Question question) {
        return QuestionResponseDto.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .category(question.getCategory().name())
                .postCategory(question.getPostCategory())
                .build();
    }


}
