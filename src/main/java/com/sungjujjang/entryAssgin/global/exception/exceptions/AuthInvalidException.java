package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class AuthInvalidException extends BusinessException {
    public static final BusinessException EXCEPTION = new AuthInvalidException();
    public AuthInvalidException() {
        super(ErrorCode.AUTH_INVALID_CREDENTIALS);
    }
}
