package com.team.puddy.domain.user.dto.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record ChangePasswordDto(@NotBlank String password) {

    @Builder
    public ChangePasswordDto {
    }
}
