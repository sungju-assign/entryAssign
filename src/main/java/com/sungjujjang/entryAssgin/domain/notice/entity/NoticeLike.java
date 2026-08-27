package com.sungjujjang.entryAssgin.domain.notice.entity;

import com.sungjujjang.entryAssgin.global.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeLike extends BaseEntity {

    @EmbeddedId
    private NoticeLikeId id;

    public NoticeLike(Long memberId, Long noticeId) {
        this.id = new NoticeLikeId(memberId, noticeId);
    }
}
