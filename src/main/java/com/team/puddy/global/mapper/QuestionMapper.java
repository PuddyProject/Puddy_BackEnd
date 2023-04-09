package com.team.puddy.global.mapper;

import com.team.puddy.domain.answer.dto.ResponseAnswerDto;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import com.team.puddy.domain.question.dto.request.UpdateQuestionDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;
import com.team.puddy.domain.user.domain.User;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "imageList", source = "imageList")
    @Mapping(target = "answerList", ignore = true)
    @Mapping(target = "isSolved", ignore = true)
    Question toEntity(RequestQuestionDto requestDto, List<Image> imageList, User user);


    default ResponseQuestionExcludeAnswerDto toDto(Question question) {
        return ResponseQuestionExcludeAnswerDto.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .isSolved(question.isSolved())
                .nickname(question.getUser().getNickname())
                .createdDate(question.getCreatedDate())
                .category(question.getCategory().name())
                .postCategory(question.getPostCategory())
                .viewCount(question.getViewCount())
                .build();
    }

    default QuestionResponseDto toDto(Question question, List<ResponseAnswerDto> answerList) {
        return QuestionResponseDto.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .isSolved(question.isSolved())
                .nickname(question.getUser().getNickname())
                .createdDate(question.getCreatedDate())
                .images(question.getImageList().stream().map(Image::getImagePath).toList())
                .category(question.getCategory().name())
                .postCategory(question.getPostCategory())
                .viewCount(question.getViewCount())
                .answerList(answerList)
                .build();
    }

    default Page<Question> toPageList(List<Question> questionList, Pageable pageable, long totalCount) {
        return new PageImpl<>(questionList, pageable, totalCount);
    }

    default QuestionListResponseDto toDto(List<ResponseQuestionExcludeAnswerDto> questionList, boolean hasNextPage) {
        return QuestionListResponseDto.builder().questionList(questionList)
                .hasNextPage(hasNextPage).build();
    }


}
