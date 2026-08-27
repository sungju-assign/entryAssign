package com.sungjujjang.entryAssgin.domain.notice.repository;

import com.sungjujjang.entryAssgin.domain.notice.entity.NoticeLike;
import com.sungjujjang.entryAssgin.domain.notice.entity.NoticeLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, NoticeLikeId> {
    boolean existsByIdMemberIdAndIdNoticeId(
            Long memberId,
            Long noticeId
    );

    Optional<NoticeLike> findByIdMemberIdAndIdNoticeId(Long memberId, Long noticeId);
}
