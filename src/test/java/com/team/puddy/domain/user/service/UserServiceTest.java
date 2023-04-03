package com.team.puddy.domain.user.service;

import com.team.puddy.domain.TestEntityUtils;
import com.team.puddy.domain.answer.domain.Answer;
import com.team.puddy.domain.answer.repository.AnswerQueryRepository;
import com.team.puddy.domain.question.domain.Question;
import com.team.puddy.domain.question.dto.response.QuestionResponeDtoExcludeAnswer;
import com.team.puddy.domain.question.repository.QuestionQueryRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.dto.request.LoginUserRequest;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.config.security.jwt.JwtTokenUtils;
import com.team.puddy.global.config.security.jwt.JwtVerifier;
import com.team.puddy.global.config.security.jwt.LoginToken;
import com.team.puddy.global.config.security.jwt.RefreshTokenRepository;
import com.team.puddy.global.mapper.UserMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserQueryRepository userQueryRepository;
    @Mock

    private QuestionQueryRepository questionQueryRepository;
    @Mock

    private AnswerQueryRepository answerQueryRepository;

    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtVerifier jwtVerifier;

    @DisplayName("로그인 성공 테스트")
    @Test
    void givenRequest_whenLogin_thenOK() {
        //given
        LoginUserRequest request = LoginUserRequest.builder().account("puddy").password("1234").build();
        User user = TestEntityUtils.user();
        LoginToken loginToken = jwtTokenUtils.createLoginToken(user);
        given(userRepository.findByAccount(any(String.class))).willReturn(Optional.of(user));
        given(jwtTokenUtils.createLoginToken(any(User.class))).willReturn(loginToken);
        given(passwordEncoder.matches(any(String.class), any(String.class))).willReturn(true);
        //when
        userService.login(request);
        //then
        verify(userRepository).findByAccount(request.account());
    }

//    @DisplayName("마이페이지 내 질문글 / 답변글 조회")
//    @Test
//    void givenUserId_whenGetMyPost_thenOK() {
//        //given
//        Long userId = 4L;
//        given(questionQueryRepository.findQuestionListByUserId(any(Long.class))).willReturn(Optional.of(List.of()));
//        given(answerQueryRepository.findAnswerListByUserId(any(Long.class))).willReturn(Optional.of(List.of()));
//        //when
//        userService.getMyPost(userId);
//        //then
//        verify(questionQueryRepository).findQuestionListByUserId(userId);
//        verify(answerQueryRepository).findAnswerListByUserId(userId);
//
//    }
}
