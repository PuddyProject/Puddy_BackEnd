package com.team.puddy.domain.article.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.article.domain.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import static com.team.puddy.domain.article.domain.QArticle.article;
import static com.team.puddy.domain.article.domain.QArticleTag.articleTag;
import static com.team.puddy.domain.comment.domain.QComment.comment;
import static com.team.puddy.domain.expert.domain.QExpert.expert;
import static com.team.puddy.domain.pet.domain.QPet.pet;
import static com.team.puddy.domain.user.domain.QUser.user;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<Article> findArticleWithUserById(Long articleId) {
        return Optional.ofNullable(
                queryFactory.selectFrom(article)
                        .leftJoin(article.user, user).fetchJoin()
                        .leftJoin(user.pet,pet).fetchJoin()
                        .leftJoin(user.expert,expert).fetchJoin()
                        .leftJoin(article.commentList,comment).fetchJoin()
                        .where(article.id.eq(articleId))
                        .fetchOne());
    }


    public Slice<Article> findArticleList(Pageable pageable) {
        List<Article> articleList = queryFactory.selectFrom(article)
                .join(article.user, user).fetchJoin()
                .leftJoin(user.expert,expert).fetchJoin()
                .orderBy(article.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        boolean hasNext = articleList.size() > pageable.getPageSize();
        if (hasNext) {
            articleList.remove(articleList.size() - 1);
        }

        return new SliceImpl<>(articleList,pageable,hasNext);
    }

    public Slice<Article> findAllByTag(String tagName, Pageable pageable) {
        List<Article> articleList = queryFactory.selectFrom(article)
                .join(articleTag).on(articleTag.article.eq(article))
                .where(
                        articleTag.tag.tagName.eq(tagName)
                )
                .orderBy(article.modifiedDate.desc())
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .fetch();

        return new SliceImpl<>(articleList,pageable,articleList.size() > pageable.getPageSize());
    }
}
