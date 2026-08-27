package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class CategoryNotFoundException extends BusinessException {
    public static final BusinessException EXCEPTION = new CategoryNotFoundException();
    public CategoryNotFoundException() {
        super(ErrorCode.CATEGORY_NOT_FOUND);
    }
}
