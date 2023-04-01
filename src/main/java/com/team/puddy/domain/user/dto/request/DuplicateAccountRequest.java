package com.team.puddy.domain.user.dto.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record DuplicateAccountRequest(@NotBlank String account) {

    @Builder

    public DuplicateAccountRequest {
    }
}
