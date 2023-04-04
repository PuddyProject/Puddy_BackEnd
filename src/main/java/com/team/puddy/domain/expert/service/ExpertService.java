package com.team.puddy.domain.expert.service;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.error.exception.user.DuplicateException;
import com.team.puddy.global.mapper.ExpertMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ExpertService {

    private final ExpertRepository expertRepository;
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;
    private final ExpertMapper expertMapper;

    public void registerExpert(RequestExpertDto requestExpertDto, Long userId){
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        if(expertRepository.existsByUserId(userId)) {
            throw new DuplicateException(ErrorCode.DUPLICATE_EXPERT);
        }
        Expert expert = expertMapper.toEntity(requestExpertDto, findUser);
        findUser.setExpert(expert);

        expertRepository.save(expert);
    }


    public ResponseExpertDto getExpertByUserId(Long userId) {
        User findUser = userQueryRepository.findUserWithExpertByUserId(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        return expertMapper.toDto(findUser.getExpert());

    }

    public List<ResponseExpertDto> getExpertList() {
        return expertRepository.findTop5ByOrderByCreatedDateDesc().stream()
                .map(expertMapper::toDto).toList();
    }
}
