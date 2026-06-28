package com.niladri.lovable_app.dto.request.member;

import com.niladri.lovable_app.enums.ProjectRole;

public record UpdateMemberRoleRequest(ProjectRole role) {
}