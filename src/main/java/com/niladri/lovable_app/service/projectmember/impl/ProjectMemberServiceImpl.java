package com.niladri.lovable_app.service.projectmember.impl;

import com.niladri.lovable_app.dto.request.member.InviteMemberRequest;
import com.niladri.lovable_app.dto.request.member.UpdateMemberRoleRequest;
import com.niladri.lovable_app.dto.response.member.MemberResponse;
import com.niladri.lovable_app.service.projectmember.IProjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectMemberServiceImpl implements IProjectMemberService {
    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        return List.of();
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        return null;
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        return null;
    }

    @Override
    public MemberResponse deleteProjectMember(Long projectId, Long memberId, Long userId) {
        return null;
    }
}
