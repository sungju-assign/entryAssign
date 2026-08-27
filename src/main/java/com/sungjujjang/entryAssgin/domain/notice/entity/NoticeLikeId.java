package com.sungjujjang.entryAssgin.domain.notice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NoticeLikeId implements Serializable {

    private Long memberId;
    private Long noticeId;

}
