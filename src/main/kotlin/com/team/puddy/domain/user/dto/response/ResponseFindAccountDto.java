package com.team.puddy.domain.user.dto.response;

import lombok.Builder;

public record ResponseFindAccountDto(String account) {

    @Builder
    public ResponseFindAccountDto {
    }
}
