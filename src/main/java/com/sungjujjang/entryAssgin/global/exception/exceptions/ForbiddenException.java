package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class ForbiddenException extends BusinessException {
    public static final BusinessException EXCEPTION = new ForbiddenException();
    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }
}
