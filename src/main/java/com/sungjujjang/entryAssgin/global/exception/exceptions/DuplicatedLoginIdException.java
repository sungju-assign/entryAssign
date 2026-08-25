package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class DuplicatedLoginIdException extends BusinessException {
    public static final BusinessException EXCEPTION = new DuplicatedLoginIdException();
    public DuplicatedLoginIdException() {
        super(ErrorCode.AUTH_DUPLICATE_LOGIN_ID);
    }
}
