package com.sungjujjang.entryAssgin.global.exception.exceptions;

import com.sungjujjang.entryAssgin.global.exception.BusinessException;
import com.sungjujjang.entryAssgin.global.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {
    public static final BusinessException EXCEPTION = new CommentNotFoundException();
    public CommentNotFoundException() {
        super(ErrorCode.COMMENT_NOT_FOUND);
    }
}
