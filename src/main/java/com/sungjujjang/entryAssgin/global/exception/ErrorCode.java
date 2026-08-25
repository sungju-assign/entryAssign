package com.sungjujjang.entryAssgin.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_FILE_TYPE(400, "INVALID_FILE_TYPE", "허용되지 않은 파일 형식입니다."),
    NOT_VALID_DTO_ERR(400, "NOT_VALID_DTO_ERR", "유효하지 않은 요청입니다."),

    AUTH_INVALID_CREDENTIALS(401, "AUTH_INVALID_CREDENTIALS", "아이디 또는 비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED(401, "UNAUTHORIZED", "인증이 필요합니다."),

    FORBIDDEN(403, "FORBIDDEN", "권한이 없습니다."),

    NOT_FOUND(404, "NOT_FOUND", "리소스가 없습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "유저를 찾을 수 없습니다."),

    AUTH_DUPLICATE_LOGIN_ID(409, "AUTH_DUPLICATE_LOGIN_ID", "이미 사용 중인 아이디입니다."),

    INTERNAL_SERVER_ERR(500, "INTERNAL_SERVER_ERR", "서버 측 오류가 발생했습니다.");

    private Integer statusCode;
    private String errorCode;
    private String errorMessage;
}
