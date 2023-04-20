package com.team.puddy.global.mapper;


import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.pet.dto.request.RequestPetDto;

import com.team.puddy.domain.pet.dto.response.ResponsePetDto;

import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PetMapper {

    default Pet toEntity(RequestPetDto request) {
        return Pet.builder()
                .name(request.name())
                .age(request.age())
                .breed(request.breed())
                .gender(request.gender())
                .weight(request.weight())
                .isNeutered(request.isNeutered())
                .note(request.note())
                .build();
    }

    default ResponsePetDto toDto(Pet pet, String imagePath) {
        return ResponsePetDto.builder()
                .name(pet.getName())
                .age(pet.getAge())
                .breed(pet.getBreed())
                .gender(pet.isGender())
                .weight(pet.getWeight())
                .imagePath(imagePath)
                .note(pet.getNote())
                .isNeutered(pet.isNeutered()).build();
    }


}
