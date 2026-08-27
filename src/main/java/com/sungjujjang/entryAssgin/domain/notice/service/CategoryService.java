package com.sungjujjang.entryAssgin.domain.notice.service;

import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryCreateRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryDeleteRequest;
import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryDto;
import com.sungjujjang.entryAssgin.domain.notice.dto.CategoryListResponse;
import com.sungjujjang.entryAssgin.domain.notice.entity.Category;
import com.sungjujjang.entryAssgin.domain.notice.repository.CategoryRepository;
import com.sungjujjang.entryAssgin.global.exception.exceptions.CategoryAlreadyExistException;
import com.sungjujjang.entryAssgin.global.exception.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryListResponse getCategories() {
        List<Category> categories =  categoryRepository.findAll();

        List<CategoryDto> categoryDtoList = categories.stream()
                .map(CategoryDto::from)
                .toList();

        return new CategoryListResponse(categoryDtoList);
    }

    @Transactional
    public Long createCategory(CategoryCreateRequest categoryCreateRequest) {
        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            throw CategoryAlreadyExistException.EXCEPTION;
        }
        Category category = Category.builder().name(categoryCreateRequest.name()).build();
        categoryRepository.save(category);

        return category.getId();
    }

    @Transactional
    public void deleteCategory(CategoryDeleteRequest categoryDeleteRequest) {
        Category category = categoryRepository.findById(categoryDeleteRequest.id())
                .orElseThrow(() -> CategoryNotFoundException.EXCEPTION);

        categoryRepository.delete(category);
    }
}
