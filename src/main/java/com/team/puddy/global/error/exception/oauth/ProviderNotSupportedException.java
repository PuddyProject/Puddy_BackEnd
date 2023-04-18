package com.team.puddy.global.error.exception.oauth;

import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;

public class ProviderNotSupportedException extends BusinessException {


    public ProviderNotSupportedException() {
        super(ErrorCode.PROVIDER_NOT_SUPPORTED);
    }
}

