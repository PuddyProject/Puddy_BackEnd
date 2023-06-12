package com.team.puddy.global.mapper;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertListDto;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ExpertMapper {

    default Expert toEntity(RequestExpertDto requestDto, User user) {
        return Expert.builder()
                .introduce(requestDto.introduce())
                .education(requestDto.education())
                .location(requestDto.location())
                .username(user.getUsername())
                .user(user)
                .careerList(requestDto.careerList())
                .build();
    }

    default ResponseExpertDto toDto(Expert findExpert,String imagePath){
        return ResponseExpertDto.builder()
                .expertId(findExpert.getId())
                .username(findExpert.getUsername())
                .introduce(findExpert.getIntroduce())
                .careerList(findExpert.getCareerList())
                .imagePath(imagePath)
                .location(findExpert.getLocation())
                .education(findExpert.getEducation())
                .build();
    }

    @Mapping(target = "hasNextPage",source = "hasNextPage")
    @Mapping(target = "expertList",source = "expertList")
    ResponseExpertListDto toListDto(List<ResponseExpertDto> expertList, boolean hasNextPage);
}
