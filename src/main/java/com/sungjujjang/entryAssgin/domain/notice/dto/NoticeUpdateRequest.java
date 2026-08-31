package com.sungjujjang.entryAssgin.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NoticeUpdateRequest(

        @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
        String title,

        @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
        String content,

        @PositiveOrZero(message = "카테고리 ID는 0 이상의 값이어야 합니다.")
        Long categoryId,

        Boolean fixed,

        @Size(max = 3, message = "파일은 최대 3개까지 첨부할 수 있습니다.")
        List<@PositiveOrZero(message = "파일 ID는 0 이상의 값이어야 합니다.") Long> files

) {}
