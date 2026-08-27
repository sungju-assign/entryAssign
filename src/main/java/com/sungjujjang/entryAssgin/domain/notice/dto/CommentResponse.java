package com.sungjujjang.entryAssgin.domain.notice.dto;

import com.sungjujjang.entryAssgin.domain.auth.dto.MeResponse;
import com.sungjujjang.entryAssgin.domain.notice.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        MeResponse author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponse from(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                MeResponse.from(comment.getMember()),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}
