package com.team.puddy.domain.answer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.answer.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team.puddy.domain.answer.domain.QAnswer.answer;
import static com.team.puddy.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
public class AnswerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Answer> getAnswerList(Long questionId) {
        return queryFactory
                .selectFrom(answer)
                .leftJoin(answer.user,user).fetchJoin()
                .where(answer.question.id.eq(questionId))
                .orderBy(answer.modifiedDate.desc())
                .fetch();
    }
}
