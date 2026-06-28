package com.niladri.lovable_app.service.auth;

import com.niladri.lovable_app.dto.request.auth.LoginRequest;
import com.niladri.lovable_app.dto.request.auth.SignupRequest;
import com.niladri.lovable_app.dto.response.auth.AuthResponse;

public interface IAuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
