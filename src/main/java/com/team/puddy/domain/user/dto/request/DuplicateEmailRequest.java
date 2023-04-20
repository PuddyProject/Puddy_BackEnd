package com.team.puddy.domain.user.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record DuplicateEmailRequest(@Email @NotBlank String email) {
}
