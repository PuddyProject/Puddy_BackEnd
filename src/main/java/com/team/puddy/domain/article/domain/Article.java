package com.team.puddy.domain.article.domain;

import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.comment.domain.Comment;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @NotBlank
    @Column(name = "title", length = 50)
    private String title;

    @NotBlank
    @Lob
    @Type(type = "text")
    private String content;

    @ColumnDefault("0")
    private long viewCount;

    private int postCategory;

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticleTag> tagList = new ArrayList<>();

    @Setter
    @Builder.Default
    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @Builder.Default
    private List<Image> imageList = new ArrayList<>();

    public void updateArticle(String title, String content,List<ArticleTag> articleTags) {
        this.title = title;
        this.content = content;
        this.tagList.clear();
        this.tagList.addAll(articleTags);
    }

    public void updateImageList(List<Image> images) {
        this.imageList.clear();
        this.imageList.addAll(images);
    }

    public void addLikes(long userId) {
        boolean exist = likeList.stream().anyMatch(like -> like.getUserId() == userId);
        if (exist) throw new BusinessException(ErrorCode.ARTICLE_LIKE_ERROR);
        Likes likes = new Likes(userId);
        this.likeList.add(likes);
        likes.setArticle(this);
    }

    public void removeLikes(long userId) {
        boolean removed = likeList.removeIf(like -> like.getUserId() == userId);
        if (!removed) throw new BusinessException(ErrorCode.ARTICLE_LIKE_ERROR);
    }


    public void setTagList(List<ArticleTag> tagList) {
        this.tagList = tagList;
    }
}