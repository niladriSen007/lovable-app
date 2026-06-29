package com.niladri.lovable_app.dto.request.project;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
        @NotBlank String name,
        @NotBlank String description
) {
}
