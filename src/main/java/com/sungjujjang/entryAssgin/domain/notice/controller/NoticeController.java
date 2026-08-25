package com.sungjujjang.entryAssgin.domain.notice.controller;

import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.FileUploadResponse;
import com.sungjujjang.entryAssgin.domain.notice.service.NoticeService;
import com.sungjujjang.entryAssgin.global.exception.exceptions.InvaildFileTypeException;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import com.sungjujjang.entryAssgin.global.s3.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping(
            value = "/file",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<FileUploadResponse> upload(
            @Valid @ModelAttribute FileUploadRequest imageUploadRequest
    ) throws IOException {
        FileUploadResponse fileUploadResponse =  noticeService.uploadFiles(imageUploadRequest.files());
        return ApiResponse.ok(fileUploadResponse);
    }
}
