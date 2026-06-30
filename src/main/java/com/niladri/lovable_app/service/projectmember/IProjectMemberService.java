package com.niladri.lovable_app.service.projectmember;

import com.niladri.lovable_app.dto.request.member.InviteMemberRequest;
import com.niladri.lovable_app.dto.request.member.UpdateMemberRoleRequest;
import com.niladri.lovable_app.dto.response.member.MemberResponse;
import com.niladri.lovable_app.entity.ProjectMember;

import java.util.List;

public interface IProjectMemberService {
    List<MemberResponse> getProjectMembers(Long projectId);

    MemberResponse inviteMember(Long projectId, InviteMemberRequest request);

    MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request);

    Void deleteProjectMember(Long projectId, Long memberId);
}
