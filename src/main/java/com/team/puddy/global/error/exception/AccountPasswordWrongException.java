package com.team.puddy.global.error.exception;

import com.team.puddy.global.error.ErrorCode;

public class AccountPasswordWrongException extends BusinessException {
    public AccountPasswordWrongException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
