package com.sungjujjang.entryAssgin.domain.notice.controller;

import com.sungjujjang.entryAssgin.domain.notice.dto.ImageUploadRequest;
import com.sungjujjang.entryAssgin.global.exception.exceptions.InvaildFileTypeException;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import com.sungjujjang.entryAssgin.global.s3.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.sungjujjang.entryAssgin.global.s3.AllowedContentType.isAllowed;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final FileService fileService;

    @PostMapping(
            value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<Void> upload(
            @Valid @ModelAttribute ImageUploadRequest imageUploadRequest
    ) throws IOException {
        List<MultipartFile> files = imageUploadRequest.files();

        for (MultipartFile file : files) {
            String contentType = file.getContentType();

            if (!isAllowed(contentType)) {
                throw InvaildFileTypeException.EXCEPTION;
            }
        }

        for (MultipartFile file : files) {
            fileService.upload(file);
        }

        return ApiResponse.ok();
    }
}
