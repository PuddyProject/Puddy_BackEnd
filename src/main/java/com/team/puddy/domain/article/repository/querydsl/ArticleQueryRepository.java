package com.team.puddy.domain.article.repository.querydsl;

import com.team.puddy.domain.article.domain.Article;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ArticleQueryRepository {

    Optional<Article> findArticleWithUserById(Long articleId);

     Slice<Article> findArticleList(Pageable pageable);

     Slice<Article> findAllByTag(String tagName, Pageable pageable);
}