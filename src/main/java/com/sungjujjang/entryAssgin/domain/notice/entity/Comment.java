package com.sungjujjang.entryAssgin.domain.notice.entity;

import com.sungjujjang.entryAssgin.domain.auth.entity.Member;
import com.sungjujjang.entryAssgin.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_id")
    private Notice notice;

    @Column(name = "notice_id", insertable = false, updatable = false)
    private Long noticeId;

    public void updateContent(String content) {
        this.content = content;
    }
    
}
