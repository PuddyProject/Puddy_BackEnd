package com.team.puddy.domain.pet.dto.request;

import lombok.Builder;

public record RequestPetDto(String name,
                            int age,
                            String breed,
                            boolean isNeutered,
                            float weight,
                            Boolean gender,
                            String note


) {
    @Builder
    public RequestPetDto {
    }
}
