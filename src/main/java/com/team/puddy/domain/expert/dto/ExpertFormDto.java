package com.team.puddy.domain.expert.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ExpertFormDto {

    @NotBlank
    private String introduce;

    @NotBlank
    private String career;

    @NotBlank
    private String location;

    @NotBlank
    private String education;
}
