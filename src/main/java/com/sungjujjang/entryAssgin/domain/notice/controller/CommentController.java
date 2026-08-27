package com.sungjujjang.entryAssgin.domain.notice.controller;

import com.sungjujjang.entryAssgin.domain.notice.dto.CommentCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.CommentListResponse;
import com.sungjujjang.entryAssgin.domain.notice.dto.CommentResponse;
import com.sungjujjang.entryAssgin.domain.notice.service.CommentService;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import com.sungjujjang.entryAssgin.global.response.IdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{noticeId}/comment")
    public ApiResponse<IdResponse<Long>> createComment(
            @PathVariable Long noticeId,
            @Valid @RequestBody CommentCreateRequest request,
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        Long commentId = commentService.createComment(noticeId, userId, request);
        return ApiResponse.ok(new IdResponse<>(commentId));
    }

    @GetMapping("/{noticeId}/comment")
    public ApiResponse<CommentListResponse> getComments(@PathVariable Long noticeId) {
        List<CommentResponse> comments = commentService.getComments(noticeId);
        return ApiResponse.ok(CommentListResponse.from(comments));
    }

    @PatchMapping("/comment/{commentId}")
    public ApiResponse<Void> updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentCreateRequest request,
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        commentService.updateComment(commentId, userId, request);
        return ApiResponse.ok();
    }

    @DeleteMapping("/comment/{commentId}")
    public ApiResponse<Void> deleteComment(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        commentService.deleteComment(commentId, userId);
        return ApiResponse.ok();
    }
}
