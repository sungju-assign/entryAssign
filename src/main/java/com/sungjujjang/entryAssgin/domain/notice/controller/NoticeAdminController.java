package com.sungjujjang.entryAssgin.domain.notice.controller;

import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadResponse;
import com.sungjujjang.entryAssgin.domain.notice.dto.NoticeCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.service.NoticeService;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import com.sungjujjang.entryAssgin.global.response.IdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/notice")
@RequiredArgsConstructor
public class NoticeAdminController {
    private final NoticeService noticeService;

    @PostMapping(
            value = "/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<FileUploadResponse> upload(
            @Valid @ModelAttribute FileUploadRequest imageUploadRequest,
            Authentication authentication
    ) throws IOException {
        Long userId = Long.valueOf(authentication.getName());
        FileUploadResponse fileUploadResponse =  noticeService.uploadFiles(imageUploadRequest.files(), userId);
        return ApiResponse.ok(fileUploadResponse);
    }

    @PostMapping("")
    public ApiResponse<IdResponse<Long>> noticeCreate(
            @Valid @RequestBody NoticeCreateRequest noticeCreateRequest,
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        Long noticeId = noticeService.createNotice(noticeCreateRequest, userId);

        IdResponse<Long> idResponse = new IdResponse<>(noticeId);

        return ApiResponse.ok(idResponse);
    }

    @PatchMapping("/{noticeId}")
    public ApiResponse<IdResponse<Long>> noticeUpdate(
            @PathVariable Long noticeId,
            @Valid @RequestBody NoticeCreateRequest noticeCreateRequest,
            Authentication authentication
    ) {
        Long userId = Long.valueOf(authentication.getName());
        Long newNoticeId = noticeService.updateNotice(noticeCreateRequest, userId, noticeId);

        IdResponse<Long> idResponse = new IdResponse<>(newNoticeId);

        return ApiResponse.ok(idResponse);
    }
}
