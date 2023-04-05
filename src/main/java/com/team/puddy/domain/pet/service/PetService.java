package com.team.puddy.domain.pet.service;

import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.pet.dto.request.RequestPetDto;
import com.team.puddy.domain.pet.dto.request.UpdatePetDto;
import com.team.puddy.domain.pet.dto.response.ResponsePetDto;
import com.team.puddy.domain.pet.repository.PetQueryRepository;
import com.team.puddy.domain.pet.repository.PetRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PetService {

    private final UserRepository userRepository;

    private final PetRepository petRepository;
    private final PetQueryRepository petQueryRepository;

    private final PetMapper petMapper;

    public void addPet(Long userId,String imagePath, RequestPetDto request) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        findUser.setPet(petRepository.save(petMapper.toEntity(findUser,request,imagePath)));
    }

    @Transactional(readOnly = true)
    public ResponsePetDto getPetByUserId(Long userId) {
        Pet findPet = petQueryRepository.findPetByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.PET_NOT_FOUND));

        return petMapper.toDto(findPet);
    }
    public void updatePet(UpdatePetDto updateDto, Long userId,String imagePath) {
        Pet findPet = petRepository.findPetByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.PET_NOT_FOUND));
        findPet.updatePet(updateDto,imagePath);
    }
}
