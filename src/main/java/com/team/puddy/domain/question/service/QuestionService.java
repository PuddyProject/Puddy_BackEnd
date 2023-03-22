package com.team.puddy.domain.question.service;

import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.global.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    public void addQuestion(QuestionRequestDto requestDto, String imagePath) throws IOException {
        Question question = questionMapper.toEntity(requestDto, imagePath);
        questionRepository.save(question);

    }

    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestion(Long questionId) {
        Question findQuestion = questionRepository.findById(questionId).orElseThrow(EntityNotFoundException::new);
        return questionMapper.toDto(findQuestion);
    }

    public List<QuestionResponseDto> getProblemList(Pageable page) {
        List<Question> questionList = questionRepository.findByOrderByModifiedDateDesc(page);

        return questionList
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }
}
