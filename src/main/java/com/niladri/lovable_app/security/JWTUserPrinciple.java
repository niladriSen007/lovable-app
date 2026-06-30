package com.niladri.lovable_app.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record JWTUserPrinciple(
        Long userId,
        String username,
        List<GrantedAuthority> authorities
) {
}
