package com.niladri.lovable_app.dto.response.project;

import java.time.Instant;

public record ProjectSummaryResponse(
        Long id,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
