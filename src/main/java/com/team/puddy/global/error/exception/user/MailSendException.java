package com.team.puddy.global.error.exception.user;

import com.team.puddy.global.error.ErrorCode;
import com.team.puddy.global.error.exception.BusinessException;

public class MailSendException extends BusinessException {

    public MailSendException() {
        super(ErrorCode.SEND_EMAIL_FAIL);
    }

}
