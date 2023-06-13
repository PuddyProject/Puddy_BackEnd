package com.team.puddy.domain.user.dto.request;

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class DuplicateEmailRequest(
    @Email @NotBlank val email : String
)

