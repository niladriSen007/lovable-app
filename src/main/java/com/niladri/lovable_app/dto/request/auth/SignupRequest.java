package com.niladri.lovable_app.dto.request.auth;

public record SignupRequest(
        String email,
        String name,
        String password
) {
}
