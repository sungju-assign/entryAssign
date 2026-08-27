package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class FileAlreadyExistsException extends BusinessException {
    public static final BusinessException EXCEPTION = new FileAlreadyExistsException();
    public FileAlreadyExistsException() {
        super(ErrorCode.FILE_ALREADY_EXISTS);
    }
}
