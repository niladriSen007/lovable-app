package com.niladri.lovable_app.controller;


import com.niladri.lovable_app.dto.request.auth.LoginRequest;
import com.niladri.lovable_app.dto.request.auth.SignupRequest;
import com.niladri.lovable_app.dto.response.ApiResponse;
import com.niladri.lovable_app.dto.response.auth.AuthResponse;
import com.niladri.lovable_app.dto.response.auth.AuthResponseWithoutToken;
import com.niladri.lovable_app.dto.response.auth.UserProfileResponse;
import com.niladri.lovable_app.service.auth.IAuthService;
import com.niladri.lovable_app.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    IAuthService authService;
    IUserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<AuthResponseWithoutToken>> signup(@RequestBody @Valid SignupRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.signup(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(authService.login(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getProfile() {
        Long userId = 1L;
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile(userId)));
    }
}
