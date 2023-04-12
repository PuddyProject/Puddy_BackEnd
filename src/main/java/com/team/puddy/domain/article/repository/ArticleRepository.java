package com.team.puddy.domain.article.repository;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.article.repository.querydsl.ArticleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article,Long>, ArticleQueryRepository {

    @Modifying
    @Query("UPDATE Article a SET a.viewCount = a.viewCount + 1 WHERE a.id = :articleId")
    void increaseViewCount(@Param("articleId") Long articleId);

    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount + 1 WHERE a.id = :articleId")
    void increaseLikeCount(@Param("articleId") Long articleId);

    @Modifying
    @Query("UPDATE Article a SET a.likeCount = a.likeCount - 1 WHERE a.id = :articleId AND a.likeCount > 0")
    void decreaseLikeCount(@Param("articleId") Long articleId);
}
