package com.team.puddy.domain.user.dto.request;

public record FindPasswordDto(String account,
                              String username,
                              String email) {
}
