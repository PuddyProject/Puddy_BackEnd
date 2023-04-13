package com.team.puddy.domain.article.repository;

import com.team.puddy.domain.article.domain.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    void deleteByArticleId(Long articleId);
}
