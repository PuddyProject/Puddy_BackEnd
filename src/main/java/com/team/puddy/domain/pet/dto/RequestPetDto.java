package com.team.puddy.domain.pet.dto;

public record RequestPetDto(String name,
                            int age,
                            String breed,
                            boolean isNeutered,
                            float weight,
                            Boolean gender,
                            String note



) {
}
