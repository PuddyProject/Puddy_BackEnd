package com.team.puddy.domain.user.dto.response;

import lombok.Builder;

public record TokenReissueDto(
        String refreshToken,
        String accessToken) {

    @Builder
    public TokenReissueDto {
    }
}
