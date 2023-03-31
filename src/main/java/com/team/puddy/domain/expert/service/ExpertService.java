package com.team.puddy.domain.expert.service;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.dto.ExpertDto;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;

    private final UserRepository userRepository;

    public Expert saveExpert(Expert expert,Long userId){
        validateDuplicateExpert(expert, userId);
        return expertRepository.save(expert);
    }

    private void validateDuplicateExpert(Expert expert, Long userId){


        Expert findExpert = expertRepository.findByUserId(userId);

        if(findExpert != null){
            throw new IllegalStateException("이미 등록된 전문가입니다.");
        }
    }

    public ExpertDto loadExpertByUserId(Long userId){

        Expert expert =expertRepository.findByUserId(userId);
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        if(expert == null){
            throw new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다.");
        }

        return ExpertDto.builder()
                .userName(findUser.getUsername())
                .introduce(expert.getIntroduce())
                .career(expert.getCareer())
                .location(expert.getLocation())
                .education(expert.getEducation()).build();

    }
}
