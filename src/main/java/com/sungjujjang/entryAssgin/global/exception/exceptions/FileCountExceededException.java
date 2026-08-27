package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class FileCountExceededException extends BusinessException {
    public static final BusinessException EXCEPTION = new FileCountExceededException();
    public FileCountExceededException() {
        super(ErrorCode.FILE_COUNT_EXCEEDED);
    }
}
