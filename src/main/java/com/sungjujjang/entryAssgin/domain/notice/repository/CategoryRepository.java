package com.sungjujjang.entryAssgin.domain.notice.repository;

import com.sungjujjang.entryAssgin.domain.notice.entity.Category;
import com.sungjujjang.entryAssgin.domain.notice.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}