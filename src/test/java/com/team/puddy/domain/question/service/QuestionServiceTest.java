package com.team.puddy.domain.question.service;

import com.team.puddy.domain.TestEntityUtils;
import com.team.puddy.domain.image.service.ImageService;
import com.team.puddy.domain.question.QuestionFixture;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.request.QuestionServiceRegister;
import com.team.puddy.domain.question.dto.request.RequestQuestionDto;

import com.team.puddy.domain.question.dto.response.ResponseQuestionExcludeAnswerDto;
import com.team.puddy.domain.question.repository.QuestionRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.EntityNotFoundException;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.AnswerMapper;
import com.team.puddy.global.mapper.QuestionMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DisplayName("질문글 서비스 테스트")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private ImageService imageService;

    @DisplayName("질문글을 등록할 수 있다.")
    @Test
    void givenRequestDto_whenAddQuestion_thenSuccess() {
        //given
        User user = TestEntityUtils.user();
        QuestionServiceRegister request = QuestionFixture.questionServiceRegister();
        Question question = TestEntityUtils.question(user);
        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(questionMapper.toEntity(any(QuestionServiceRegister.class), any(List.class), any(User.class))).willReturn(question);
        //when
        questionService.addQuestion(request, Collections.emptyList(), Objects.requireNonNull(user).getId());

        //then
        verify(questionRepository).save(any());
    }

    @DisplayName("질문글을 리스트로 조회할 수 있다.")
    @Test
    void givenPage_whenGetQuestionList_thenSuccess() {
        //given
        PageRequest page = PageRequest.of(1, 10);
        User user = TestEntityUtils.user();
        TestEntityUtils.questionList();
        Page<Question> questionPage = new PageImpl<>(Collections.singletonList(
                TestEntityUtils.question(user)
        ));
        given(questionRepository.findByTitleStartWithOrderByCreatedDateDesc(any(), any(String.class))).willReturn(questionPage);
        //when
        questionService.getQuestionListByTitleStartWith(page, "", "desc");
        //then
        verify(questionRepository).findByTitleStartWithOrderByCreatedDateDesc(page, "");
    }

    @DisplayName("질문글의 총 개수를 조회할 수 있다.")
    @Test
    void givenNothing_whenGetCount_then200() {
        //given
        given(questionRepository.count()).willReturn(20L);
        //when
        questionService.getQuestionCount();
        //then
        verify(questionRepository).count();
    }

    @DisplayName("특정 질문글을 조회할 수 있다.")
    @Test
    void givenQuestionId_whenGetQuestion_thenQuestionResponseDto() {
        Long questionId = 2L;
        User user = TestEntityUtils.user();
        Optional<Question> question = Optional.ofNullable(TestEntityUtils.question(user));
        //given
        given(questionRepository.getQuestion(any(Long.class))).willReturn(question);
        //when
        questionService.getQuestion(questionId);
        //then
        verify(questionRepository).getQuestion(questionId);

    }

    @DisplayName("질문글 조회시 질문글 조회수가 1만큼 증가한다.")
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

    @DisplayName("존재하지 않는 질문글의 조회수를 증가시키려 하면 예외가 발생한다.")
    @Test
    void givenNonExistQuestionId_whenIncreaseViewCount_thenThrowsNotFoundException() {
        //given
        Long nonExistentQuestionId = 100L;
        given(questionRepository.existsById(any(Long.class))).willReturn(false);

        //when & then
        assertThatThrownBy(() -> questionService.increaseViewCount(nonExistentQuestionId))
                .isInstanceOf(NotFoundException.class);
    }
}
