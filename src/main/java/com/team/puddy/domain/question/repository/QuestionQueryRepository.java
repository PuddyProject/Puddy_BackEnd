package com.team.puddy.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.question.domain.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.puddy.domain.question.domain.QQuestion.question;

@Repository
@RequiredArgsConstructor
public class QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;


    public List<Question> getPopularQuestionList() {

        return queryFactory.selectFrom(question)
                .orderBy(question.view_count.desc())
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