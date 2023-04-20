package com.team.puddy.global.config.security.jwt;

import lombok.Builder;

@Builder
public record LoginToken(String accessToken,
                         String refreshToken) {
}