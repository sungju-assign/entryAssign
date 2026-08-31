package com.sungjujjang.entryAssgin.domain.notice.dto;

import java.util.List;

public record NoticeListResponse(
        List<NoticeSimpleDto> notices,
        Long totalElements,
        int totalPages,
        int number
) {
}
