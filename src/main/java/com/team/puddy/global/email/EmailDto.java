package com.team.puddy.global.email;

import lombok.Builder;

public record EmailDto(String toAddress,
                       String title,
                       String message,
                       String fromAddress) {

    @Builder
    public EmailDto {
    }
}
