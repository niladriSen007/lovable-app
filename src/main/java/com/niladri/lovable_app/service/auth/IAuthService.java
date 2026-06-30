package com.niladri.lovable_app.service.auth;

import com.niladri.lovable_app.dto.request.auth.LoginRequest;
import com.niladri.lovable_app.dto.request.auth.SignupRequest;
import com.niladri.lovable_app.dto.response.auth.AuthResponse;
import com.niladri.lovable_app.dto.response.auth.AuthResponseWithoutToken;
import com.niladri.lovable_app.dto.response.auth.RefreshResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    AuthResponseWithoutToken signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

    RefreshResponse refreshToken(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse);
}
