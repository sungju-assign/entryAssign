package com.sungjujjang.entryAssgin.domain.notice.dto;

import com.sungjujjang.entryAssgin.domain.auth.dto.MeResponse;
import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeDto(
        Long id,
        String title,
        String content,
        MeResponse author,
        Long likeCount,
        Long viewCount,
        boolean isFixed,
        Long categoryId,
        LocalDateTime createdAt
) {

    public static NoticeDto from(Notice notice) {
        return new NoticeDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                MeResponse.from(notice.getMember()),
                notice.getLikeCount(),
                notice.getViewCount(),
                notice.isFixed(),
                notice.getCategoryId(),
                notice.getCreatedAt()
        );
    }
}
