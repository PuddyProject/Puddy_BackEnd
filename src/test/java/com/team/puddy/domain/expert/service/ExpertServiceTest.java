package com.team.puddy.domain.expert.service;

import com.team.puddy.domain.TestEntityUtils;
import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.config.WithMockAuthUser;
import com.team.puddy.global.mapper.ExpertMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)

public class ExpertServiceTest {

    @InjectMocks
    private ExpertService expertService;

    @Mock
    private ExpertRepository expertRepository;
    @Mock
    private UserQueryRepository userQueryRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ExpertMapper expertMapper;

    @WithMockAuthUser
    @DisplayName("전문가 등록 API")
    @Test
    void givenRequestExpertDto_whenAddExpert_thenOK() {
        //given
        User user = TestEntityUtils.user();
        Expert expert = TestEntityUtils.expert();
        RequestExpertDto requestDto = TestEntityUtils.requestExpertDto();
        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(expertRepository.existsByUserId(any(Long.class))).willReturn(false);
        given(expertMapper.toEntity(any(RequestExpertDto.class),any(User.class))).willReturn(expert);
        //when
        expertService.registerExpert(requestDto,user.getId());

        //then
        verify(userRepository).findById(any());
        verify(expertRepository).existsByUserId(any());
        verify(expertMapper).toEntity(requestDto,user);
    }
}
