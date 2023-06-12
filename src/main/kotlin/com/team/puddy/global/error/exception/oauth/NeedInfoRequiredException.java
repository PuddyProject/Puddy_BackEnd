package com.team.puddy.global.error.exception.oauth;

import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;

public class NeedInfoRequiredException extends BusinessException {
    public NeedInfoRequiredException() {
        super(ErrorCode.NEED_MORE_INFO);
    }

}
