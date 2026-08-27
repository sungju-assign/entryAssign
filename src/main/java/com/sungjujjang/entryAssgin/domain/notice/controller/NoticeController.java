package com.sungjujjang.entryAssgin.domain.notice.controller;

import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryListResponse;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.NoticeDto;
import com.sungjujjang.entryAssgin.domain.notice.dto.UpdateLikeResponse;
import com.sungjujjang.entryAssgin.domain.notice.service.CategoryService;
import com.sungjujjang.entryAssgin.domain.notice.service.NoticeService;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final CategoryService categoryService;

    @GetMapping("/{noticeId}")
    public ApiResponse<NoticeDto> getNotice(
            @PathVariable Long noticeId,
            Authentication authentication,
            HttpServletRequest request
    ) {
        NoticeDto noticeDto;
        if (authentication == null) {
            noticeDto = noticeService.getNotice(null, noticeId, request);
        } else {
            Long userId = Long.valueOf(authentication.getName());
            noticeDto = noticeService.getNotice(userId, noticeId, request);
        }

        return ApiResponse.ok(noticeDto);
    }

    @GetMapping("/{noticeId}/like")
    public ApiResponse<UpdateLikeResponse> updateNoticeLike(
            @PathVariable Long noticeId,
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        boolean likeStatus = noticeService.updateLike(userId, noticeId);
        UpdateLikeResponse updateLikeResponse = new UpdateLikeResponse(likeStatus);
        return ApiResponse.ok(updateLikeResponse);
    }

    @GetMapping("/category")
    public ApiResponse<CategoryListResponse> getCategories() {
        CategoryListResponse categoryListResponse = categoryService.getCategories();
        return ApiResponse.ok(categoryListResponse);
    }

}
