package com.niladri.lovable_app.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JWTService {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            JWTConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8)
    );

    public String generateAccessToken(UserInfoService userInfoService) {
        return generateToken(userInfoService);
    }

    public String generateRefreshToken(UserInfoService userInfoService) {
        return Jwts.builder()
                .subject(userInfoService.getId().toString())
                .issuedAt(new Date()).expiration(
                        new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 * 6) // 6 months
                ).signWith(secretKey)
                .compact();
    }

    private String generateToken(UserInfoService userInfoService) {
        return Jwts.builder()
                .subject(userInfoService.getUsername()) // email
                .claim("userId", userInfoService.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 * 60000)) // 15 minute
                .signWith(secretKey)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.parseLong(claims.get("userId", String.class));
    }

    public JWTUserPrinciple validateToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Long userId = Long.parseLong(claims.get("userId", String.class));
        String email = claims.getSubject();
        return new JWTUserPrinciple(userId, email, new ArrayList<>());
    }

    public Long getLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !(authentication.getPrincipal() instanceof JWTUserPrinciple)) {
            throw new AuthenticationCredentialsNotFoundException("No JWT Found");
        }
        return ((JWTUserPrinciple) authentication.getPrincipal()).userId();
    }
}
