package com.sungjujjang.entryAssgin.domain.notice.dto;

import com.sungjujjang.entryAssgin.domain.auth.dto.MeResponse;
import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeSimpleDto(
        Long id,
        String title,
        Long likeCount,
        Long viewCount,
        MeResponse author,
        boolean isFixed,
        LocalDateTime createdAt,
        Long categoryId
) {
    public static NoticeSimpleDto from(Notice notice) {
        return new NoticeSimpleDto(
                notice.getId(),
                notice.getTitle(),
                notice.getLikeCount(),
                notice.getViewCount(),
                MeResponse.from(notice.getMember()),
                notice.isFixed(),
                notice.getCreatedAt(),
                notice.getCategoryId()
        );
    }
}
