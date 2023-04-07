package com.team.puddy.domain.pet.service;

import com.team.puddy.domain.image.domain.Image;
import com.team.puddy.domain.image.service.ImageService;
import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.pet.dto.request.RequestPetDto;
import com.team.puddy.domain.pet.dto.request.UpdatePetDto;
import com.team.puddy.domain.pet.dto.response.ResponsePetDto;
import com.team.puddy.domain.pet.repository.PetQueryRepository;
import com.team.puddy.domain.pet.repository.PetRepository;
import com.team.puddy.domain.user.domain.User;
import com.team.puddy.domain.user.repository.UserQueryRepository;
import com.team.puddy.domain.user.repository.UserRepository;
import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.NotFoundException;
import com.team.puddy.global.mapper.PetMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PetService {

    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    private final PetRepository petRepository;
    private final PetQueryRepository petQueryRepository;
    private final ImageService imageService;
    private final PetMapper petMapper;

    public void addPet(Long userId, MultipartFile file, RequestPetDto request) throws IOException {
        User findUser = userQueryRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        Pet pet = petMapper.toEntity(findUser, request);
        if (file != null && !file.isEmpty()) { //이미지가 있을 경우
            Image savedimage = imageService.uploadImageForPets(file);
            pet.setImage(savedimage);
        }
        findUser.setPet(pet);
    }

    @Transactional(readOnly = true)
    public ResponsePetDto getPetByUserId(Long userId) {
        Pet findPet = petQueryRepository.findPetByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.PET_NOT_FOUND));

        return petMapper.toDto(findPet);
    }
    @Transactional
    public void updatePet(UpdatePetDto updateDto, Long userId, MultipartFile file) throws IOException {
        Pet findPet = petRepository.findPetByUserId(userId).orElseThrow(() -> new NotFoundException(ErrorCode.PET_NOT_FOUND));
        Image findImage = findPet.getImage();
        if (file != null && !file.isEmpty()) { //이미지가 있을 경우
            Image savedImage = imageService.uploadImageForPets(file);
            if (findImage == null) { // 해당 펫의 이미지가 없는 경우
                findPet.setImage(savedImage);
            } else { // 해당 펫의 이미지가 있는 경우
                imageService.deleteImage(findImage);
                findImage.updateImage(savedImage.getImagePath(), savedImage.getOriginalName());
            }
        }
        findPet.updatePet(updateDto);
    }
}
