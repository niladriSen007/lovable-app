package com.niladri.lovable_app.controller;

import com.niladri.lovable_app.dto.request.member.InviteMemberRequest;
import com.niladri.lovable_app.dto.request.member.UpdateMemberRoleRequest;
import com.niladri.lovable_app.dto.response.ApiResponse;
import com.niladri.lovable_app.dto.response.member.MemberResponse;
import com.niladri.lovable_app.entity.ProjectMember;
import com.niladri.lovable_app.service.projectmember.IProjectMemberService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectMemberController {

    IProjectMemberService projectMemberService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getProjectMembers(@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(projectMemberService.getProjectMembers(projectId, userId),HttpStatus.OK.value())
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> inviteMember(
            @PathVariable Long projectId,
            @RequestBody @Valid InviteMemberRequest request
    ) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(projectMemberService.inviteMember(projectId, request, userId), HttpStatus.CREATED.value())
        );
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberResponse>> updateMemberRole(
            @PathVariable Long projectId,
            @PathVariable Long memberId,
            @RequestBody @Valid UpdateMemberRoleRequest request
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(projectMemberService.updateMemberRole(projectId, memberId, request, userId), HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteProjectMember(
            @PathVariable Long projectId,
            @PathVariable Long memberId
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(projectMemberService.deleteProjectMember(projectId, memberId, userId), HttpStatus.OK.value())
        );
    }
}