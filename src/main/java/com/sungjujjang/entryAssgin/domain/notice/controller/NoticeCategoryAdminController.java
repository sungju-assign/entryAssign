package com.sungjujjang.entryAssgin.domain.notice.controller;

import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryDeleteRequest;
import com.sungjujjang.entryAssgin.domain.notice.service.CategoryService;
import com.sungjujjang.entryAssgin.global.response.ApiResponse;
import com.sungjujjang.entryAssgin.global.response.IdResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/notice")
@RequiredArgsConstructor
public class NoticeCategoryAdminController {
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ApiResponse<IdResponse<Long>> createCategory(
        @Valid @RequestBody CategoryCreateRequest categoryCreateRequest
    ) {

        Long categoryId = categoryService.createCategory(categoryCreateRequest);
        IdResponse<Long> idResponse = new IdResponse<>(categoryId);

        return ApiResponse.ok(idResponse);
    }

    @DeleteMapping("/category")
    public ApiResponse<Void> deleteCategory(
        @Valid @RequestBody CategoryDeleteRequest categoryDeleteRequest
    ) {

        categoryService.deleteCategory(categoryDeleteRequest);

        return ApiResponse.ok();
    }
}
