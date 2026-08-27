package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class FileNotFoundException extends BusinessException {
    public static final BusinessException EXCEPTION = new FileNotFoundException();
    public FileNotFoundException() {
        super(ErrorCode.FILE_NOT_FOUND);
    }
}
