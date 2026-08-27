package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class FixedCountExceededException extends BusinessException {
    public static final BusinessException EXCEPTION = new FixedCountExceededException();
    public FixedCountExceededException() {
        super(ErrorCode.FIXED_COUNT_EXCEEDED);
    }
}
