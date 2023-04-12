package com.team.puddy.domain.question.repository.querydsl;

import com.team.puddy.domain.question.domain.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public interface QuestionQueryRepository {

    public Optional<Question> getQuestion(Long questionId);

    public Slice<Question> getQuestionList(Pageable pageable);

    List<Question> getPopularQuestionList();

    List<Question> getRecentQuestionList();

    Optional<Question> findByIdWithUser(Long questionId);

    List<Question> findQuestionListByUserId(Long userId);

    Question findQuestionWithImageAndUser(Long questionId);

    Optional<Question> findQuestionForUpdate(Long questionId, Long userId);

    Optional<Question> findQuestionForDelete(Long questionId, Long userId);
}