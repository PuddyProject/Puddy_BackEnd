package com.team.puddy.domain.expert.service;


import com.team.puddy.domain.expert.domain.Expert;
import com.team.puddy.domain.expert.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExpertService {

    private final ExpertRepository expertRepository;

    public Expert saveExpert(Expert expert){
        validateDuplicateExpert(expert);
        return expertRepository.save(expert);
    }

    private void validateDuplicateExpert(Expert expert){
        Expert findExpert = expertRepository.findByCompanyNm(expert.getCompanyNm());

        if(findExpert != null){
            throw new IllegalStateException("이미 등록된 전문가입니다.");
        }
    }



}
