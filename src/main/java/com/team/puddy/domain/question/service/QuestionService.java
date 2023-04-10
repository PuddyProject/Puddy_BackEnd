package com.team.puddy.domain.question.service;

import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.repository.ImageRepository;
import com.team.puddy.domain.image.service.ImageService;
import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;
import com.team.puddy.domain.question.dto.request.UpdateQuestionDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;
import com.team.puddy.domain.question.repository.QuestionQueryRepository;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.type.Category;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.common.S3UpdateUtil;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.error.exception.user.UnAuthorizedException;
import com.team.puddy.global.mapper.AnswerMapper;
import com.team.puddy.global.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public QuestionListResponseDto getQuestionList(Pageable page) {
        Slice<Question> questionList = questionQueryRepository.getQuestionList(page);
        return questionMapper.toDto(questionList.stream().map(questionMapper::toDto).toList(), questionList.hasNext());

    }

    @Transactional(readOnly = true)
    public List<ResponseQuestionExcludeAnswerDto> getPopularQuestions() {
        List<Question> popularQuestions = questionQueryRepository.getPopularQuestionList();
        return popularQuestions.stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ResponseQuestionExcludeAnswerDto> getRecentQuestions() {
        List<Question> recentQuestionList = questionQueryRepository.getRecentQuestionList();
        return recentQuestionList.stream()
                .map(questionMapper::toDto)
                .toList();
    }


    @Transactional(readOnly = true)
    public QuestionResponseDto getQuestion(Long questionId) {
        Question findquestion = questionQueryRepository.getQuestion(questionId).orElseThrow(
                () -> new NotFoundException(ErrorCode.QUESTION_NOT_FOUND));

        Pet findpet = findquestion.getUser().getPet(); // Pet이 없으면 null 반환
        return questionMapper.toDto(findquestion, findpet, findquestion.getAnswerList().stream()
                .map(answer -> answerMapper.toDto(answer, answer.getUser())).toList());
    }

    @Transactional
    public void increaseViewCount(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new NotFoundException(ErrorCode.QUESTION_NOT_FOUND);
        }
        questionRepository.increaseViewCount(questionId);
    }

    @Transactional(readOnly = true)
    public long getQuestionCount() {
        return questionRepository.count();
    }

    @Transactional
    public void addQuestion(RequestQuestionDto requestDto, List<MultipartFile> images, Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        List<Image> imageList = imageService.saveImageListToQuestion(images);

        Question question = questionMapper.toEntity(requestDto, imageList, findUser);
        questionRepository.save(question);

    }

    @Transactional
    public void updateQuestion(Long questionId, UpdateQuestionDto requestDto, List<MultipartFile> images, Long userId) {
        Question findQuestion = questionQueryRepository.findQuestionForUpdate(questionId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNAUTHORIZED_OPERATION));

        TrySaveImageList(findQuestion, images);

        findQuestion.updateQuestion(requestDto.title(), requestDto.content(), requestDto.category());

    }

    private void TrySaveImageList(Question findQuestion, List<MultipartFile> images) {
        //요청으로 들어온 이미지가 null이 아닐 경우에만 이미지 삭제 시도
        if (images != null && !images.isEmpty()) {
            // 기존의 이미지를 지운다.
            List<Image> findImages = findQuestion.getImageList();
            if (findImages != null) {
                findImages.forEach(imageService::deleteImage);
            }
            //새로운 이미지를 저장한다.
            List<Image> imageList = imageService.saveImageListToQuestion(images);
            //질문글을 수정한다.
            findQuestion.updateImageList(imageList);
        }
    }

    @Transactional
    public void deleteQuestion(Long questionId, Long userId) {
        Question findQuestion = questionQueryRepository.findQuestionForDelete(questionId, userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.UNAUTHORIZED_OPERATION));
        //S3에 저장된 이미지를 지운다.
        List<Image> findImages = findQuestion.getImageList();
        if (findImages != null) {
            findImages.forEach(imageService::deleteImage);
        }
        //질문글을 지운다.
        questionRepository.deleteById(questionId);
    }
}
