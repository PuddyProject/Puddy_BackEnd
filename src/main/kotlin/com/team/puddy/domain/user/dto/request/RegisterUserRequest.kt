package com.team.puddy.domain.user.dto.request;

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class RegisterUserRequest(
    @NotBlank val account: String,
    @NotBlank val password: String,
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 아닙니다.")
    val email: String,
    val isNotificated: Boolean,
)
