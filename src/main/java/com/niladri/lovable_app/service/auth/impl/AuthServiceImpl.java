package com.niladri.lovable_app.service.auth.impl;

import com.niladri.lovable_app.dto.request.auth.LoginRequest;
import com.niladri.lovable_app.dto.request.auth.SignupRequest;
import com.niladri.lovable_app.dto.response.auth.AuthResponse;
import com.niladri.lovable_app.dto.response.auth.AuthResponseWithoutToken;
import com.niladri.lovable_app.dto.response.auth.RefreshResponse;
import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.exceptions.InvalidCredentialsException;
import com.niladri.lovable_app.exceptions.UserAlreadyExistsException;
import com.niladri.lovable_app.mapper.UserMapper;
import com.niladri.lovable_app.repository.UserRepository;
import com.niladri.lovable_app.security.JWTService;
import com.niladri.lovable_app.security.UserInfoService;
import com.niladri.lovable_app.service.auth.IAuthService;
import com.niladri.lovable_app.service.session.impl.SessionService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements IAuthService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    AuthenticationManager authenticationManager;
    JWTService jWtService;
    SessionService sessionService;


    @Override
    public AuthResponseWithoutToken signup(SignupRequest request) {
        log.info("Signup request received in service - AuthServiceImpl");

        // Firstly we will have to check the user email already exists in the database
        // or not
        Optional<UserEntity> userEntity = userRepository.findByEmail(request.email());
        if (userEntity.isPresent()) {
            throw new UserAlreadyExistsException("User already exists for the user - " + request.email());
        }

        // Creating the UserEntity from the request payload
        UserEntity user = UserEntity.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        // Saving the user into the database
        UserEntity registeredUser = userRepository.save(user);

        return new AuthResponseWithoutToken(userMapper.toUserProfileResponse(registeredUser));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticateUser(request.email(), request.password());
        UserInfoService principal = (UserInfoService) authentication.getPrincipal();
        String accessToken = jWtService.generateAccessToken(principal);
        String refreshToken = jWtService.generateRefreshToken(principal);
        sessionService.generateNewSession(principal.getUsername(), refreshToken);
        return new AuthResponse(accessToken, refreshToken, userMapper.toUserProfileResponseFromUserInfoService(principal));
    }

    @Override
    public RefreshResponse refreshToken(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse) {
        log.info("Attempting to refresh token");
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null || cookies.length == 0) {
            log.warn("No cookies present on refresh-token request");
            throw new AuthenticationServiceException("Refresh token not found");
        }
        Cookie refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found"));
        log.info("Received request to refresh token");
        String refreshTokenValue = refreshToken.getValue();
        log.info("Refresh token value: {}", refreshTokenValue);

        boolean isValidSession = sessionService.validateSession(refreshTokenValue);

        if (!isValidSession)
            throw new InvalidCredentialsException("Invalid refresh token");

        Long userIdFromToken = jWtService.getUserIdFromToken(refreshTokenValue);
        Optional<UserEntity> userFromToken = userRepository.findById(userIdFromToken);

        if (userFromToken.isEmpty())
            throw new InvalidCredentialsException("User not found");

        if (jWtService.validateToken(refreshTokenValue) == null) {
            throw new JwtException("Invalid refresh token");
        }
        String accessToken = jWtService.generateAccessToken((UserInfoService) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("Refresh token validated and new tokens generated for user: {}", userFromToken.get().getEmail());
        return new RefreshResponse(
                "Refresh token validated",
                "New access token generated successfully",
                accessToken,
                refreshTokenValue
        );
    }


    private Authentication authenticateUser(String email, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
