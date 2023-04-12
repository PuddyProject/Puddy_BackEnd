package com.team.puddy.domain.article.repository.querydsl;

import com.team.puddy.domain.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

import static com.team.puddy.domain.article.domain.QArticle.article;
import static com.team.puddy.domain.user.domain.QUser.user;

public interface ArticleQueryRepository {

    Optional<Article> findArticleWithUserById(Long articleId);

    Slice<Article> findArticleList(Pageable pageable);

    Slice<Article> findAllByTag(String tagName, Pageable pageable);

    Slice<Article> findAllByTitleStartingWithOrderByModifiedDateDesc(String keyword, Pageable pageable);

    List<Article> findPopularArticleList();

    List<Article> findRecentArticleList();


}