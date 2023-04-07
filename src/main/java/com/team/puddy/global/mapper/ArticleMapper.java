package com.team.puddy.global.mapper;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.article.dto.request.RequestArticleDto;
import com.team.puddy.domain.article.dto.response.ResponseArticleDto;
import com.team.puddy.domain.article.dto.response.TagDto;
import com.team.puddy.domain.comment.dto.response.ResponseCommentDto;
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
public interface ArticleMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "user",source = "user")
    @Mapping(target = "tagList", ignore = true)
    @Mapping(target = "imageList",source = "imageList")
    @Mapping(target = "commentList",ignore = true)
    @Mapping(target = "viewCount",ignore = true)
    @Mapping(target = "likeCount",ignore = true)
    Article toEntity(RequestArticleDto request, List<Image> imageList, User user);

    default ResponseArticleDto toDto(Article article, List<ResponseCommentDto> commentDtoList) {
        return ResponseArticleDto.builder()
                .articleId(article.getId())
                .createdDate(article.getCreatedDate())
                .nickname(article.getUser().getNickname())
                .title(article.getTitle())
                .content(article.getContent())
                .images(article.getImageList().stream().map(Image::getImagePath).toList())
                .tagList(article.getTagList())
                .commentList(commentDtoList)
                .viewCount(article.getViewCount())
                .likeCount(article.getLikeCount())
                .postCategory(article.getPostCategory())
                .build();
    }
}
