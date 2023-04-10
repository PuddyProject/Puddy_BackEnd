package com.team.puddy.domain.article.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.article.domain.Article;
import com.team.puddy.domain.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import static com.team.puddy.domain.article.domain.QArticle.article;
import static com.team.puddy.domain.article.domain.QTag.tag;
import static com.team.puddy.domain.comment.domain.QComment.comment;
import static com.team.puddy.domain.expert.domain.QExpert.expert;
import static com.team.puddy.domain.image.domain.QImage.image;
import static com.team.puddy.domain.pet.domain.QPet.pet;
import static com.team.puddy.domain.question.domain.QQuestion.question;
import static com.team.puddy.domain.user.domain.QUser.user;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleQueryRepository {

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
                .leftJoin(article.imageList,image).fetchJoin()
                .orderBy(article.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(articleList,pageable,articleList.size() > pageable.getPageSize());
    }

}
