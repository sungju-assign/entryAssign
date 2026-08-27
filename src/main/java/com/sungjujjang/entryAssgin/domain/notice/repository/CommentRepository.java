package com.sungjujjang.entryAssgin.domain.notice.repository;

import com.sungjujjang.entryAssgin.domain.notice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
        SELECT c FROM Comment c
        WHERE c.notice.id = :noticeId
        ORDER BY c.createdAt ASC, c.id ASC
    """)
    List<Comment> findByNoticeId(@Param("noticeId") Long noticeId);
}
