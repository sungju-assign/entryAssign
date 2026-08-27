package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class CategoryAlreadyExistException extends BusinessException {
    public static final BusinessException EXCEPTION = new CategoryAlreadyExistException();
    public CategoryAlreadyExistException() {
        super(ErrorCode.CATEGORY_ALREADY_EXISTS);
    }
}
