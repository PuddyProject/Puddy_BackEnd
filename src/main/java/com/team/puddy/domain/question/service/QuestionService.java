package com.team.puddy.domain.question.service;

import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.service.ImageService;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponeDtoExcludeAnswer;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.repository.QuestionQueryRepository;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.AnswerMapper;
import com.team.puddy.global.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.team.puddy.global.error.exception.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuestionQueryRepository questionQueryRepository;

    private final UserRepository userRepository;

    private final QuestionMapper questionMapper;

    private final AnswerMapper answerMapper;

    private final ImageService imageService;

    @Transactional(readOnly = true)
    public QuestionListResponseDto getQuestionList(Pageable page) {
        Page<Question> questionList = questionQueryRepository.getQuestionList(page);
        return questionMapper.toDto(questionList.stream().map(questionMapper::toDto).toList(), questionList.hasNext());

    }

    @Transactional(readOnly = true)
    public List<QuestionResponeDtoExcludeAnswer> getPopularQuestions() {
        List<Question> popularQuestions = questionQueryRepository.getPopularQuestionList();
        return popularQuestions.stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionResponeDtoExcludeAnswer> getRecentQuestions() {
        List<Question> recentQuestionList = questionQueryRepository.getRecentQuestionList();
        return recentQuestionList.stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestion(Long questionId) {
        Question question = questionQueryRepository.getQuestion(questionId);
        return questionMapper.toDto(question, question.getAnswerList().stream()
                .map(answer -> answerMapper.toDto(answer, answer.getUser())).toList());
    }

    @Transactional
    public void increaseViewCount(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException();
        }
        questionRepository.increaseViewCount(questionId);
    }

    @Transactional(readOnly = true)
    public long getQuestionCount() {
        return questionRepository.count();
    }

    public void addQuestion(RequestQuestionDto requestDto, List<MultipartFile> images, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Image> imageList = processImages(images);

        Question question = questionMapper.toEntity(requestDto, imageList, findUser);
        questionRepository.save(question);

    }

    public void updateQuestion(RequestQuestionDto requestDto, List<MultipartFile> images) {
        //TODO
    }

    public List<Image> processImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return new ArrayList<>();
        }

        return images.stream()
                .map(multipartFile -> {
                    try {
                        return imageService.uploadImageForQuestions(multipartFile);
                    } catch (IOException e) {
                        // 예외 처리 코드 (예: 로그 남기기)
                        throw new BusinessException(ErrorCode.IMAGE_PROCESSING_ERROR);
                    }
                })
                .toList();
    }
}
