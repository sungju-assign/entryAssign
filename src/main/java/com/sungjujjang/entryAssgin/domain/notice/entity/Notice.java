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
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    private Long likeCount = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    @Column(nullable = false)
    private boolean isFixed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = true)
    private Category category;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="last_modified_member_id")
    private Member lastModifiedBy;

    public void updateNotice(
            String title,
            String content,
            Category category,
            Boolean isFixed,
            Member lastModifiedBy
    ) {
        if (title != null) this.title = title;
        if (content != null) this.content = content;
        if (category != null) this.category = category;
        if (isFixed != null) this.isFixed = isFixed;
        this.lastModifiedBy = lastModifiedBy;
    }
}
