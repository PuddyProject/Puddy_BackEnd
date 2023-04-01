package com.team.puddy.domain.question.service;

import com.team.puddy.domain.TestEntityUtils;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionRequestDto;
import com.team.puddy.domain.question.dto.response.QuestionListResponseDto;
import com.team.puddy.domain.question.dto.response.QuestionResponeDtoExcludeAnswer;
import com.team.puddy.domain.question.dto.response.QuestionResponseDto;
import com.team.puddy.domain.question.repository.QuestionQueryRepository;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.exception.EntityNotFoundException;
import com.team.puddy.global.mapper.AnswerMapper;
import com.team.puddy.global.mapper.QuestionMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuestionQueryRepository questionQueryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private AnswerMapper answerMapper;


    @DisplayName("문제 등록 테스트")
    @Test
    void givenRequestDto_whenAddQuestion_thenSuccess() throws IOException {
        //given
        User user = TestEntityUtils.user();
        QuestionRequestDto requestDto = TestEntityUtils.questionRequestDto();
        Question question = TestEntityUtils.question(user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(questionMapper.toEntity(any(QuestionRequestDto.class),any(String.class),any(User.class))).willReturn(question);
        //when
        questionService.addQuestion(requestDto,"", Objects.requireNonNull(user).getId());

        //then
        verify(questionRepository).save(any());
    }

    @DisplayName("문제 목록 조회 테스트")
    @Test
    void givenPage_whenGetQuestionList_thenSuccess() {
        //given
        PageRequest page = PageRequest.of(1, 10);
        User user = TestEntityUtils.user();
        TestEntityUtils.questionList();
        Page<Question> questionPage = new PageImpl<>(Collections.singletonList(
                TestEntityUtils.question(user)
        ));
        List<QuestionResponeDtoExcludeAnswer> questionList = List.of(
                QuestionResponeDtoExcludeAnswer.builder().content("s").build()
        );
        Page<QuestionResponeDtoExcludeAnswer> questions = TestEntityUtils.questionPageList();
        given(questionQueryRepository.getQuestionList(any(Pageable.class))).willReturn(questionPage);
        //when
        questionService.getQuestionList(page);
        //then
        verify(questionQueryRepository).getQuestionList(page);
    }

    @DisplayName("문제 전체 개수 조회 테스트")
    @Test
    void givenNothing_whenGetCount_then200() {
        //given
        given(questionRepository.count()).willReturn(20L);
        //when
        questionService.getQuestionCount();
        //then
        verify(questionRepository).count();
    }

    @DisplayName("질문글 단건 조회 테스트")
    @Test
    void givenQuestionId_whenGetQuestion_thenQuestionResponseDto() {
        Long questionsId = 2L;
        User user = TestEntityUtils.user();
        Question question = TestEntityUtils.question(user);
        //given
        given(questionQueryRepository.getQuestion(any(Long.class))).willReturn(question);
        //when
        questionService.getQuestion(questionsId);
        //then
        verify(questionQueryRepository).getQuestion(questionsId);

    }

    @DisplayName("조회수 증가 테스트")
    @Test
    void givenQuestionId_whenIncreaseViewCount_then200() {
        //given
        Long questionsId = 2L;
        given(questionRepository.existsById(any(Long.class))).willReturn(true);
        //when
        questionService.increaseViewCount(questionsId);
        //then
        verify(questionRepository).existsById(questionsId);

    }

    @Transactional
    public void increaseViewCount(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new EntityNotFoundException();
        }
        questionRepository.increaseViewCount(questionId);
    }




}
