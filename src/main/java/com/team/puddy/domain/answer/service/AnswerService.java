package com.team.puddy.domain.answer.service;

import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.answer.dto.RequestAnswerDto;
import com.team.puddy.domain.answer.dto.ResponseAnswerDto;
import com.team.puddy.domain.answer.repository.AnswerQueryRepository;
import com.team.puddy.domain.answer.repository.AnswerRepository;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.repository.QuestionQueryRepository;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.AnswerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final AnswerQueryRepository answerQueryRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final QuestionQueryRepository questionQueryRepository;


    private final AnswerMapper answerMapper;

    public void addAnswer(RequestAnswerDto requestDto, Long userId, Long questionId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        Question findQuestion = questionRepository.findById(questionId).orElseThrow(() -> new NotFoundException(ErrorCode.QUESTION_NOT_FOUND));

        Answer answer = answerMapper.toEntity(requestDto, findUser, findQuestion);
//        findQuestion.getAnswerList().add(answer);
        answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public List<ResponseAnswerDto> getAnswerList(Long questionId) {
        return answerQueryRepository.getAnswerList(questionId)
                .stream()
                .map(answer -> answerMapper.toDto(answer, answer.getUser()))
                .toList();
    }

    @Transactional(readOnly = true)
    public Long getAnswerCount() {
        return answerRepository.count();
    }

    public void answerSelect(Long questionId, Long answerId,Long userId) {
        Question findQuestion = questionQueryRepository.findByIdWithUser(questionId).orElseThrow(() -> new NotFoundException(ErrorCode.QUESTION_NOT_FOUND));
        if (!findQuestion.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.NOT_THE_WRITER,ErrorCode.NOT_THE_WRITER.getMessage());
        }
        questionRepository.select(questionId);
        answerRepository.select(answerId);
    }
}
