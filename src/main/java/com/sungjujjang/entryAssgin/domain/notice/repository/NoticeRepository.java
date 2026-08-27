package com.sungjujjang.entryAssgin.domain.notice.repository;

import com.sungjujjang.entryAssgin.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Modifying
    @Query("""
        UPDATE Notice n
        SET n.viewCount = n.viewCount + 1
        WHERE n.id = :noticeId
    """)
    void increaseViewCount(@Param("noticeId") Long noticeId);

    @Modifying
    @Query("""
        UPDATE Notice n
        SET n.likeCount = n.likeCount + 1
        WHERE n.id = :noticeId
    """)
    int increaseLikeCount(@Param("noticeId") Long noticeId);

    @Modifying
    @Query("""
        UPDATE Notice n
        SET n.likeCount = n.likeCount - 1
        WHERE n.id = :noticeId AND n.likeCount > 0
    """)
    int decreaseLikeCount(@Param("noticeId") Long noticeId);

    long countByIsFixedTrue();
}
