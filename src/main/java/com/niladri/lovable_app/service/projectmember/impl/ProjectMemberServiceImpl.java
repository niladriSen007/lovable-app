package com.niladri.lovable_app.service.projectmember.impl;

import com.niladri.lovable_app.dto.request.member.InviteMemberRequest;
import com.niladri.lovable_app.dto.request.member.UpdateMemberRoleRequest;
import com.niladri.lovable_app.dto.response.member.MemberResponse;
import com.niladri.lovable_app.entity.Project;
import com.niladri.lovable_app.entity.ProjectMember;
import com.niladri.lovable_app.entity.ProjectMemberId;
import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.exceptions.*;
import com.niladri.lovable_app.mapper.ProjectMemberMapper;
import com.niladri.lovable_app.repository.ProjectMemberRepository;
import com.niladri.lovable_app.repository.ProjectRepository;
import com.niladri.lovable_app.repository.UserRepository;
import com.niladri.lovable_app.security.JWTService;
import com.niladri.lovable_app.service.projectmember.IProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProjectMemberServiceImpl implements IProjectMemberService {

    ProjectMemberRepository projectMemberRepository;
    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMemberMapper projectMemberMapper;
    JWTService jwtService;

    @Override
    @PreAuthorize("@security.canViewMembers(#projectId)")
    public List<MemberResponse> getProjectMembers(Long projectId) {
        return projectMemberRepository.findByProjectMemberIdProjectId(projectId).stream().map(
                projectMemberMapper::toMemberResponseFromProjectMember
        ).toList();
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request) {
        Project accessibleProjectDetails = getAccessibleProjectDetails(projectId, jwtService.getLoggedInUserId());

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotExistException("User with email: " + request.email() + " does not exist"));

        if (user.getId().equals(jwtService.getLoggedInUserId())) {
            throw new CannotSendInviteToYourselfException("Owner cannot be invited as a member");
        }

        // Check if the user already exists in the project member list or not
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, user.getId());
        if (projectMemberRepository.existsById(projectMemberId)) {
            throw new UserIsAlreadyInMemberListException("User with email: " + request.email()
                    + " is already a member of the project with id: " + projectId);
        }

        ProjectMember projectMember = ProjectMember.builder()
                .projectMemberId(projectMemberId)
                .project(accessibleProjectDetails)
                .user(user)
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .invitedBy(user)
                .build();

        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

        return projectMemberMapper.toMemberResponseFromProjectMember(savedProjectMember);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request) {

        // Creating the composite primary key for the project member
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new UserIsNotInMemberListException("User with id: " + memberId + " is not a member of the project with id: " + projectId));
        projectMember.setProjectRole(request.role());
        ProjectMember updatedProjectMember = projectMemberRepository.save(projectMember);
        return projectMemberMapper.toMemberResponseFromProjectMember(updatedProjectMember);
    }

    @Override
    @PreAuthorize("@security.canManageMembers(#projectId)")
    public Void deleteProjectMember(Long projectId, Long memberId) {

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if (!projectMemberRepository.existsById(projectMemberId)) {
            throw new UserIsNotInMemberListException("User with id: " + memberId + " is not a member of the project with id: " + projectId);
        }
        projectMemberRepository.deleteById(projectMemberId);
        return null;
    }

    private Project getAccessibleProjectDetails(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectsByUserId(userId, projectId)
                .orElseThrow(() -> new NotActualOwnerOfProjectException("User with id: " + userId + " is not the owner of project with id: " + projectId));
    }
}
