package com.team.puddy.domain.expert.service;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.RequestExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertDto;
import com.team.puddy.domain.expert.dto.ResponseExpertListDto;
import com.team.puddy.domain.expert.dto.UpdateExpertDto;
import com.team.puddy.domain.expert.repository.ExpertQueryRepository;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final ExpertQueryRepository expertQueryRepository;
    private final UserQueryRepository userQueryRepository;
    private final ExpertMapper expertMapper;

    public void registerExpert(RequestExpertDto requestExpertDto, Long userId){
        User findUser = userQueryRepository.findByIdWithExpert(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        if(findUser.getExpert() != null) {
            throw new DuplicateException(ErrorCode.DUPLICATE_EXPERT);
        }
        Expert expert = expertMapper.toEntity(requestExpertDto, findUser);
        findUser.setExpert(expert);
        expertRepository.save(expert);
    }


    public ResponseExpertDto getExpertByUserId(Long userId) {
        Expert findExpert = expertQueryRepository.findByIdWithUser(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.EXPERT_NOT_FOUND));

        return expertMapper.toDto(findExpert,findExpert.getUser().getImage());

    }

    public void updateExpert(Long userId, UpdateExpertDto update) {
        Expert findExpert = expertRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.EXPERT_NOT_FOUND));
        findExpert.updateExpert(update.username(), update.location(), update.education(), update.introduce());
        findExpert.updateCareerList(update.careerList());
    }

    public List<ResponseExpertDto> getRecentExperts() {
        return expertQueryRepository.findExpertListForMainPage().stream().map(expert -> expertMapper.toDto(expert,expert.getUser().getImage())).toList();
    }

    public ResponseExpertListDto getExpertList(Pageable pageable) {
        Slice<Expert> expertList = expertQueryRepository.findExpertList(pageable);
        List<ResponseExpertDto> expertDtoList = expertList.stream().map(expert -> expertMapper.toDto(expert, expert.getUser().getImage())).toList();
        return expertMapper.toListDto(expertDtoList,expertList.hasNext());

    }}


