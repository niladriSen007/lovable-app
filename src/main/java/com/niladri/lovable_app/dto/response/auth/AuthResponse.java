package com.niladri.lovable_app.dto.response.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {

}
