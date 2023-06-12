package com.team.puddy.domain.user.dto.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record RegisterUserRequest(@NotBlank String account,
                                  @NotBlank String password,
                                  @NotBlank String username,
                                  @NotBlank(message = "이메일을 입력해주세요")
                                  @Email(message = "이메일 형식이 아닙니다.") String email,
                                  boolean isNotificated ) {

    @Builder
    public RegisterUserRequest {}
}
