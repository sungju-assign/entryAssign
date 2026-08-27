package com.sungjujjang.entryAssgin.domain.notice.repository;

import com.sungjujjang.entryAssgin.domain.notice.entity.File;
import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findFilesByNotice(Notice notice);

    Integer countAllByNotice(Notice notice);
}
