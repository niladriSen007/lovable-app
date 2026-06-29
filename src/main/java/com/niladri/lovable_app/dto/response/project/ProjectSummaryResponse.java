package com.niladri.lovable_app.dto.response.project;

import com.niladri.lovable_app.enums.ProjectRole;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        ProjectRole  projectRole,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
