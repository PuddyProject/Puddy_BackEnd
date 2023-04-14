package com.team.puddy.domain.article.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "user_article", columnNames = {"user_id", "article_id"})
})
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private long id;

    @NotNull
    @Column(name = "user_id")
    private long userId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Like(long userId) {
        this.userId = userId;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}