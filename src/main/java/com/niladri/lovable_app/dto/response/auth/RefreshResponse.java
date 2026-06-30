package com.niladri.lovable_app.dto.response.auth;

public record RefreshResponse(
        String title,
        String message,
        String accessToken,
        String refreshToken
) {
}
