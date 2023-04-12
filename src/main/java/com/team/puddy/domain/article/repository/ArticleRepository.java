package com.team.puddy.domain.article.repository;

import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.article.repository.querydsl.ArticleQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long>, ArticleQueryRepository {
}
