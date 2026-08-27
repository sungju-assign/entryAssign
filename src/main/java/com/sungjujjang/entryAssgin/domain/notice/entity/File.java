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
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String objectKey;

    @Column(nullable = false)
    private String OriginalName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = true)
    private Member author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="notice_id", nullable = true)
    private Notice notice;

    public void updateNotice(Notice notice) {
        this.notice = notice;
    }
}
