package com.team.puddy.global.mapper;

import com.team.puddy.domain.comment.domain.Comment;
import com.team.puddy.domain.comment.dto.response.ResponseCommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CommentMapper {

    ResponseCommentDto toDto(Comment comment,String nickname);
}
