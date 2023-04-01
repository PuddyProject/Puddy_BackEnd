package com.team.puddy.domain.expert.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public record RequestExpertDto(@NotBlank
                               String introduce,

                               @NotNull
                               List<String> careerList,

                               @NotBlank
                               String location,

                               @NotBlank
                               String education) {

    @Builder
    public RequestExpertDto {
    }
}
