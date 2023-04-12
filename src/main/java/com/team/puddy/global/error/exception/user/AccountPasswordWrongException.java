package com.team.puddy.global.error.exception.user;

import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;

public class AccountPasswordWrongException extends BusinessException {
    public AccountPasswordWrongException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
