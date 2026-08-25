package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class InvaildFileTypeException extends BusinessException {
    public static final BusinessException EXCEPTION = new InvaildFileTypeException();
    public InvaildFileTypeException() {
        super(ErrorCode.INVALID_FILE_TYPE);
    }
}
