package com.team.puddy.domain.expert.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class ExpertFormDto {

    private Long id;

    @NotBlank(message = "사업자 번호는 필수 입력 값입니다.")
    private String companyNm;

    @NotBlank(message = "소개는 필수 입력 값입니다.")
    private String introduction;

    @NotBlank(message = "병원이름 필수 입력 값입니다.")
    private String companyName;
    @NotBlank(message = "경력사항은 필수 입력 값입니다.")
    private String career;
    @NotBlank(message = "병원위치는 필수 입력 값입니다.")
    private String location;
}
