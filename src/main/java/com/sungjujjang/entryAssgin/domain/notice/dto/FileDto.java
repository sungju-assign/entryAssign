package com.sungjujjang.entryAssgin.domain.notice.dto;

import com.sungjujjang.entryAssgin.domain.notice.entity.File;

public record FileDto(
        Long fileId,
        String fileName,
        String originalName
) {
    public static FileDto from(File file) {
        return new FileDto(file.getId(), file.getObjectKey(), file.getOriginalName());
    }
}
