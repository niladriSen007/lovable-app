package com.niladri.lovable_app.service.user.impl;

import com.niladri.lovable_app.dto.response.auth.UserProfileResponse;
import com.niladri.lovable_app.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}
