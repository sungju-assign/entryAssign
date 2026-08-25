package com.sungjujjang.entryAssgin.domain.auth.dto;

import com.sungjujjang.entryAssgin.domain.auth.entity.Member;
import com.sungjujjang.entryAssgin.domain.auth.enums.Role;

public record MeResponse(
        Long userId,
        String userName,
        Role role
) {
    public static MeResponse from(Member member) {
        return new MeResponse(
                member.getId(),
                member.getUsername(),
                member.getRole()
        );
    }
}
