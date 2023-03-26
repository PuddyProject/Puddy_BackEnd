package com.team.puddy.domain.answer.service;

import com.team.puddy.domain.answer.dto.RequestAnswerDto;
import com.team.puddy.domain.answer.repository.AnswerRepository;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.AnswerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final AnswerMapper answerMapper;

    public void addAnswer(RequestAnswerDto requestDto, Long userId, Long questionId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Question findQuestion = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException(ErrorCode.QUESTION_NOT_FOUND));

        answerRepository.save(answerMapper.toEntity(requestDto,findUser,findQuestion));
    }

    public Long getAnswerCount() {
        return answerRepository.count();
    }
}
