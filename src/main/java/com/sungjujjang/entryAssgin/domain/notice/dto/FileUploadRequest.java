package com.sungjujjang.entryAssgin.domain.notice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FileUploadRequest(
        @NotEmpty(message = "파일을 최소 1개 이상 업로드해야 합니다.")
        @Size(max = 3, message = "파일은 최대 3개까지 업로드할 수 있습니다.")
        List<MultipartFile> files
) {
}