package com.team.puddy.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.global.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.puddy.domain.question.domain.QQuestion.question;
import static com.team.puddy.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;

    private final QuestionMapper questionMapper;

    public Question getQuestion(Long questionId) {
        return queryFactory
                .selectFrom(question)
                .leftJoin(question.user, user).fetchJoin() // User 엔티티를 함께 조회하기 위한 조인
                .where(question.id.eq(questionId)) // 주어진 questionId에 해당하는 엔티티를 조회하기 위한 조건
                .fetchOne();
    }

    public Page<Question> getQuestionList(Pageable pageable) {
        List<Question> questionList = queryFactory.selectFrom(question)
                .join(question.user, user).fetchJoin()
                .orderBy(question.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory.selectFrom(question)
                .fetch().size();

        return new PageImpl<>(questionList, pageable, totalCount);

    }


    public List<Question> getPopularQuestionList() {

        return queryFactory.selectFrom(question)
                .orderBy(question.viewCount.desc())
                .limit(5)
                .fetch();
    }

    public List<Question> getRecentQuestionList() {
        return queryFactory.selectFrom(question)
                .orderBy(question.modifiedDate.desc())
                .limit(5)
                .fetch();
    }
}