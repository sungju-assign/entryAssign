package com.sungjujjang.entryAssgin.domain.notice.dto;

import java.util.List;

public record CommentListResponse(
        List<CommentResponse> comments,
        long totalCount
) {
    public static CommentListResponse from(List<CommentResponse> comments) {
        return new CommentListResponse(comments, comments.size());
    }
}
