package com.team.puddy.global.mapper;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.review.domain.Review;
import com.team.puddy.domain.review.dto.RequestReviewDto;
import com.team.puddy.domain.review.dto.ResponseReviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ReviewMapper {


    @Mapping(target = "id",ignore = true)
    Review toEntity(RequestReviewDto reviewDto, Expert expert);

    default ResponseReviewDto toDto(Review review) {
        return ResponseReviewDto.builder()
                .content(review.getContent()).build();
    }
}
