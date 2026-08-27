package com.sungjujjang.entryAssgin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    FIXED_COUNT_EXCEEDED(400, "FIXED_COUNT_EXCEEDED", "고정 공지는 3개를 초과할 수 없습니다."),
    FILE_COUNT_EXCEEDED(400, "FILE_COUNT_EXCEEDED", "파일 개수 제한을 초과했습니다."),
    INVALID_FILE_TYPE(400, "INVALID_FILE_TYPE", "허용되지 않은 파일 형식입니다."),
    NOT_VALID_DTO_ERR(400, "NOT_VALID_DTO_ERR", "유효하지 않은 요청입니다."),

    AUTH_INVALID_CREDENTIALS(401, "AUTH_INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),

    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),

    CATEGORY_NOT_FOUND(404, "CATEGORY_NOT_FOUND", "카테고리를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(404, "COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다."),
    FILE_NOT_FOUND(404, "FILE_NOT_FOUND", "파일을 찾을 수 없습니다."),
    NOTICE_NOT_FOUND(404, "NOTICE_NOT_FOUND", "공지를 찾을 수 없습니다."),
    NOT_FOUND(404, "NOT_FOUND", "리소스가 없습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "유저를 찾을 수 없습니다."),

    AUTH_DUPLICATE_LOGIN_ID(409, "AUTH_DUPLICATE_LOGIN_ID", "이미 사용 중인 아이디입니다."),
    CATEGORY_ALREADY_EXISTS(409, "CATEGORY_ALREADY_EXISTS", "카테고리가 이미 존재합니다."),
    FILE_ALREADY_EXISTS(409, "FILE_ALREADY_EXISTS", "이미 등록된 파일입니다."),

    INTERNAL_SERVER_ERR(500, "INTERNAL_SERVER_ERR", "서버 측 오류가 발생했습니다.");

    private Integer statusCode;
    private String errorCode;
    private String errorMessage;
}
