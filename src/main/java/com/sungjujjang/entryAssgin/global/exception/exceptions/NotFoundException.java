package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class NotFoundException extends BusinessException {
    public static final BusinessException EXCEPTION = new NotFoundException();
    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
