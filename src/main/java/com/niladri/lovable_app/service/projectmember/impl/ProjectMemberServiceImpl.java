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
import com.niladri.lovable_app.mapper.UserMapper;
import com.niladri.lovable_app.repository.ProjectMemberRepository;
import com.niladri.lovable_app.repository.ProjectRepository;
import com.niladri.lovable_app.repository.UserRepository;
import com.niladri.lovable_app.service.projectmember.IProjectMemberService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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

    @Override
    public List<MemberResponse> getProjectMembers(Long projectId, Long userId) {
        Project accessibleProjectDetails = getAccessibleProjectDetails(projectId, userId);

        List<MemberResponse> memberResponseList = new ArrayList<>();
        memberResponseList.add(projectMemberMapper.toMemberResponse(accessibleProjectDetails.getOwner()));

        memberResponseList.addAll(
                projectMemberRepository.findByProjectMemberIdProjectId(projectId).stream().map(
                        projectMemberMapper::toMemberResponseFromProjectMember
                ).toList()
        );

        return memberResponseList;
    }

    @Override
    public MemberResponse inviteMember(Long projectId, InviteMemberRequest request, Long userId) {
        Project accessibleProjectDetails = getAccessibleProjectDetails(projectId, userId);

        if (!accessibleProjectDetails.getOwner().getId().equals(userId)) {
            throw new NotActualOwnerOfProjectException("User with id: " + userId + " is not the owner of project with id: " + projectId);
        }

        UserEntity user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotExistException("User with email: " + request.email() + " does not exist"));

        if (user.getId().equals(userId)) {
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
                .projectRole(request.role())
                .invitedAt(Instant.now())
                .invitedBy(user)
                .build();

        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

        return projectMemberMapper.toMemberResponseFromProjectMember(savedProjectMember);
    }

    @Override
    public MemberResponse updateMemberRole(Long projectId, Long memberId, UpdateMemberRoleRequest request, Long userId) {
        Project accessibleProjectDetails = getAccessibleProjectDetails(projectId, userId);
        if(!accessibleProjectDetails.getOwner().getId().equals(userId)) {
            throw new NotActualOwnerOfProjectException("User with id: " + userId + " is not the owner of project with id: " + projectId);
        }

        // Creating the composite primary key for the project member
        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        ProjectMember projectMember = projectMemberRepository.findById(projectMemberId)
                .orElseThrow(() -> new UserIsNotInMemberListException("User with id: " + memberId + " is not a member of the project with id: " + projectId));
        projectMember.setProjectRole(request.role());
        ProjectMember updatedProjectMember = projectMemberRepository.save(projectMember);
        return projectMemberMapper.toMemberResponseFromProjectMember(updatedProjectMember);
    }

    @Override
    public Void deleteProjectMember(Long projectId, Long memberId, Long userId) {
        Project accessibleProjectDetails = getAccessibleProjectDetails(projectId, userId);
        if (!accessibleProjectDetails.getOwner().getId().equals(userId)) {
            throw new NotActualOwnerOfProjectException("User with id: " + userId + " is not the owner of project with id: " + projectId);
        }

        ProjectMemberId projectMemberId = new ProjectMemberId(projectId, memberId);
        if(!projectMemberRepository.existsById(projectMemberId)) {
            throw new UserIsNotInMemberListException("User with id: " + memberId + " is not a member of the project with id: " + projectId);
        }
        projectMemberRepository.deleteById(projectMemberId);
    }

    private Project getAccessibleProjectDetails(Long projectId, Long userId) {
        return projectRepository.findAccessibleProjectsByOwnerId(userId, projectId)
                .orElseThrow(() -> new NotActualOwnerOfProjectException("User with id: " + userId + " is not the owner of project with id: " + projectId));
    }
}
