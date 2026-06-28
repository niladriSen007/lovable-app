package com.niladri.lovable_app.service.user;

import com.niladri.lovable_app.dto.response.auth.UserProfileResponse;

public interface IUserService {
    UserProfileResponse getProfile(Long userId);
}
