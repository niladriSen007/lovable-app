package com.niladri.lovable_app.mapper;

import com.niladri.lovable_app.dto.response.auth.UserProfileResponse;
import com.niladri.lovable_app.dto.response.member.MemberResponse;
import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.security.UserInfoService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserProfileResponse toUserProfileResponse(UserEntity user);
    UserProfileResponse toUserProfileResponseFromUserInfoService(UserInfoService userInfoService);
}
