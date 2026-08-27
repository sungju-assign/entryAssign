package com.sungjujjang.entryAssgin.domain.notice.dto;

import com.sungjujjang.entryAssgin.domain.notice.entity.Category;

public record CategoryDto(
        Long id,
        String name
) {

    public static CategoryDto from(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
