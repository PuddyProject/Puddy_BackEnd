package com.team.puddy.domain.user.dto.request;

import javax.validation.constraints.NotBlank;

public record DuplicateAccountRequest(@NotBlank String account) {
}
