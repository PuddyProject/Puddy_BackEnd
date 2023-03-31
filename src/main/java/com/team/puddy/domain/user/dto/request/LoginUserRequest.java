package com.team.puddy.domain.user.dto.request;

import lombok.Builder;

public record LoginUserRequest(String account, String password) {
    @Builder
    public LoginUserRequest {
    }
}