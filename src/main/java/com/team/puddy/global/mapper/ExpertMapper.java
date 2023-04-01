package com.team.puddy.global.mapper;

import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ExpertMapper {

    default Expert toEntity(RequestExpertDto requestDto, User user) {
        return Expert.builder()
                .introduce(requestDto.introduce())
                .education(requestDto.education())
                .location(requestDto.location())
                .careerList(requestDto.careerList())
                .username(user.getUsername()).build();
    }


    ResponseExpertDto toDto(Expert findExpert);
}
