package com.team.puddy.global.error.exception.user;

import com.team.puddy.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException{
    private final ErrorCode errorCode = ErrorCode.UNAUTHORIZED_OPERATION;
}