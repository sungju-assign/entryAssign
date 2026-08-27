package com.sungjujjang.entryAssgin.domain.notice.dto;

import java.util.List;

public record CategoryListResponse(
        List<CategoryDto> categories
) {
}
