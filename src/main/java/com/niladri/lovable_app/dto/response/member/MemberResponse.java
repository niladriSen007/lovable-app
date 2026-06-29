package com.niladri.lovable_app.dto.response.member;

import com.niladri.lovable_app.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String name,
        String email,
        String avatarUrl,
        ProjectRole role,
        Instant invitedAt
) {
}
