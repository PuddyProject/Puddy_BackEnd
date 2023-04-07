package com.team.puddy.domain.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.global.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.team.puddy.domain.answer.domain.QAnswer.answer;
import static com.team.puddy.domain.expert.domain.QExpert.expert;
import static com.team.puddy.domain.image.domain.QImage.image;
import static com.team.puddy.domain.pet.domain.QPet.pet;
import static com.team.puddy.domain.question.domain.QQuestion.question;
import static com.team.puddy.domain.user.domain.QUser.user;

@Repository
@RequiredArgsConstructor
@Slf4j
public class QuestionQueryRepository {

    private final JPAQueryFactory queryFactory;


    public Optional<Question> getQuestion(Long questionId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(question)
                .leftJoin(question.user, user).fetchJoin() // User 엔티티를 함께 조회하기 위한 조인
                .leftJoin(user.pet, pet).fetchJoin() // Pet 엔티티를 함께 조회하기 위한 조인
                .leftJoin(user.expert, expert).fetchJoin() // Expert 엔티티를 함께 조회하기 위한 조인
                .leftJoin(question.answerList, answer).fetchJoin() // Answer 엔티티를 함께 조회하기 위한 조인
                .where(question.id.eq(questionId)) // 주어진 questionId에 해당하는 엔티티를 조회하기 위한 조건
                .fetchOne());
    }

    public Slice<Question> getQuestionList(Pageable pageable) {
        List<Question> questionList = queryFactory.selectFrom(question)
                .join(question.user, user).fetchJoin()
                .orderBy(question.modifiedDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        return new SliceImpl<>(questionList, pageable, questionList.size() > pageable.getPageSize());

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

    public Optional<Question> findByIdWithUser(Long questionId) {
        return Optional.ofNullable(queryFactory.selectFrom(question)
                .leftJoin(question.user, user).fetchJoin()
                .where(question.id.eq(questionId))
                .fetchOne());
    }

    public List<Question> findQuestionListByUserId(Long userId) {
        return queryFactory.selectFrom(question)
                .innerJoin(question.user, user)
                .fetchJoin()
                .where(user.id.eq(userId))
                .fetch();
    }

    public Question findQuestionWithImageAndUser(Long questionId) {
        return queryFactory
                .selectFrom(question)
                .leftJoin(question.imageList, image).fetchJoin()
                .leftJoin(question.user, user).fetchJoin()
                .where(question.id.eq(questionId))
                .fetchOne();
    }

}