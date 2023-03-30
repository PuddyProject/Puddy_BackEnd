package com.team.puddy.domain.pet.dto.response;

import lombok.Builder;

public record ResponsePetDto(String name,
                             int age,
                             String breed,
                             boolean isNeutered,
                             float weight,
                             Boolean gender,
                             String note,
                             String imagePath



) {

    @Builder
    public ResponsePetDto {
    }
}
