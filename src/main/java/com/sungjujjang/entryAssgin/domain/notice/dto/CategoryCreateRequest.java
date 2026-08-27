package com.sungjujjang.entryAssgin.domain.notice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryCreateRequest(
        @NotBlank(message = "이름은 필수입니다.")
        @Size(max = 20, message = "최대 20자까지 입력 가능합니다.")
        String name
) {
}
