package com.niladri.lovable_app.mapper;

import com.niladri.lovable_app.dto.response.member.MemberResponse;
import com.niladri.lovable_app.entity.ProjectMember;
import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.enums.ProjectRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMemberMapper {


    @Mapping(target = "userId", source = "id")
    @Mapping(target="role",constant = "OWNER")
    MemberResponse toMemberResponse(UserEntity member);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "role", source = "projectRole")
    MemberResponse toMemberResponseFromProjectMember(ProjectMember member);
}
