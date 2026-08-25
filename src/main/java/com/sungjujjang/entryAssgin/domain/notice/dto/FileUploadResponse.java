package com.sungjujjang.entryAssgin.domain.notice.dto;

import java.util.List;

public record FileUploadResponse(
        List<FileDto> files
) {
}
