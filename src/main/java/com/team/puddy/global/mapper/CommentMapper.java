package com.team.puddy.global.mapper;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.comment.domain.Comment;
import com.team.puddy.domain.comment.dto.request.RequestCommentDto;
import com.team.puddy.domain.comment.dto.response.ResponseCommentDto;
import com.team.puddy.domain.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CommentMapper {

    ResponseCommentDto toDto(Comment comment,String nickname);

    default Comment toEntity(RequestCommentDto request, Article article, User user) {
        return Comment.builder()
                .user(user)
                .article(article)
                .content(request.content())
                .postCategory(request.postCategory()).build();
    }
}
