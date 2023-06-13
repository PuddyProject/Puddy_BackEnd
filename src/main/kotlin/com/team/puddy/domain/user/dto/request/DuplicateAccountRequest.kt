package com.team.puddy.domain.user.dto.request

import jakarta.validation.constraints.NotBlank

data class DuplicateAccountRequest(@NotBlank val account: String) {
}
