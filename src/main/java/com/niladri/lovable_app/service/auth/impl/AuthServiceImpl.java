package com.niladri.lovable_app.service.auth.impl;

import com.niladri.lovable_app.dto.request.auth.LoginRequest;
import com.niladri.lovable_app.dto.request.auth.SignupRequest;
import com.niladri.lovable_app.dto.response.auth.AuthResponse;
import com.niladri.lovable_app.service.auth.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    @Override
    public AuthResponse signup(SignupRequest request) {
        return null;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        return null;
    }
}
