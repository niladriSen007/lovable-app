package com.niladri.lovable_app.dto.request.auth;

public record LoginRequest(
        String email,
        String password
) {
}
