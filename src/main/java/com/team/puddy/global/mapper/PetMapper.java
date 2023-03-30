package com.team.puddy.global.mapper;

import com.team.puddy.domain.pet.domain.Pet;
import com.team.puddy.domain.pet.dto.request.RequestPetDto;
import com.team.puddy.domain.pet.dto.response.ResponsePetDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PetMapper {

    default Pet toEntity(RequestPetDto request,String imagePath) {
        return Pet.builder()
                .name(request.name())
                .age(request.age())
                .breed(request.breed())
                .gender(request.gender())
                .imagePath(imagePath)
                .weight(request.weight())
                .isNeutered(request.isNeutered())
                .note(request.note())
                .build();
    }

    ResponsePetDto toDto(Pet pet);
}
