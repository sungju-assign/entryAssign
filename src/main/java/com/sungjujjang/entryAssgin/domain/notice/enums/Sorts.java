package com.sungjujjang.entryAssgin.domain.notice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sorts {
    latest("createdAt"),
    added("createdAt"),
    like("likeCount"),
    view("viewCount");

    private final String sort;
}
