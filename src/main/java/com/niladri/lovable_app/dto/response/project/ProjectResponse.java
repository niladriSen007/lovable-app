package com.niladri.lovable_app.dto.response.project;

import com.niladri.lovable_app.dto.response.auth.UserProfileResponse;

import java.time.Instant;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt,
        UserProfileResponse owner
) {
}
