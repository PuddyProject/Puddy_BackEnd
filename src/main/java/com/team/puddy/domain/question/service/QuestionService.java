package com.team.puddy.domain.question.service;

import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    private final QuestionMapper questionMapper;
    @Transactional(readOnly = true)
    public QuestionListResponseDto getQuestionList(Pageable page) {
        Page<Question> findQuestionList = questionRepository.findByOrderByModifiedDateDesc(page);
        boolean hasNextPage = findQuestionList.hasNext();
        List<QuestionResponseDto> questionResponseDtoList = findQuestionList
                .stream()
                .map(questionMapper::toDto)
                .toList();
        return QuestionListResponseDto.builder().questionList(questionResponseDtoList)
                .hasNextPage(hasNextPage)
                .build();



    }
    public void addQuestion(QuestionRequestDto requestDto, String imagePath,Long userId) throws IOException {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Question question = questionMapper.toEntity(requestDto, imagePath,findUser);
        questionRepository.save(question);

    }


    @Transactional(readOnly = true)
    public QuestionResponseDto  getQuestion(Long questionId) {
        Question findQuestion = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        return questionMapper.toDto(findQuestion);
    }

    @Transactional(readOnly = true)
    public long getQuestionCount() {
        return questionRepository.count();
    }

}
