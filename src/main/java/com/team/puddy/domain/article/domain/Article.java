package com.team.puddy.domain.article.domain;

import com.team.puddy.domain.BaseTimeEntity;
import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.comment.domain.Comment;
import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.type.Category;
import com.team.puddy.domain.user.domain.User;
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

    @ColumnDefault("0")
    private long likeCount;

    private int postCategory;

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

    public void setTagList(List<ArticleTag> tagList) {
        this.tagList = tagList;
    }
}