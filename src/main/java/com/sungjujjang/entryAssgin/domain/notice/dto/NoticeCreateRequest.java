package com.sungjujjang.entryAssgin.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record NoticeCreateRequest(

        @NotBlank(message = "제목은 필수입니다.")
        @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
        String title,

        @NotBlank(message = "내용은 필수입니다.")
        @Size(max = 5000, message = "내용은 최대 5000자까지 입력할 수 있습니다.")
        String content,

        @Positive(message = "카테고리 ID는 1 이상의 값이어야 합니다.")
        int categoryId,

        boolean fixed,

        List<@Positive(message = "파일 ID는 1 이상의 값이어야 합니다.") Integer> files

) {
}