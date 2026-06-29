package com.niladri.lovable_app.service.project.impl;

import com.niladri.lovable_app.dto.request.project.ProjectRequest;
import com.niladri.lovable_app.dto.response.project.ProjectResponse;
import com.niladri.lovable_app.dto.response.project.ProjectSummaryResponse;
import com.niladri.lovable_app.entity.Project;
import com.niladri.lovable_app.entity.ProjectMember;
import com.niladri.lovable_app.entity.ProjectMemberId;
import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.enums.ProjectRole;
import com.niladri.lovable_app.exceptions.NotActualOwnerOfProjectException;
import com.niladri.lovable_app.exceptions.ProjectNotExistException;
import com.niladri.lovable_app.exceptions.SameProjectDetailsException;
import com.niladri.lovable_app.exceptions.UserNotExistException;
import com.niladri.lovable_app.mapper.ProjectMapper;
import com.niladri.lovable_app.projection.ProjectWithRole;
import com.niladri.lovable_app.repository.ProjectMemberRepository;
import com.niladri.lovable_app.repository.ProjectRepository;
import com.niladri.lovable_app.repository.UserRepository;
import com.niladri.lovable_app.service.project.IProjectService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class ProjectService implements IProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;
    ProjectMemberRepository projectMemberRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        UserEntity owner = userExistence(userId);
        Project project = Project.builder()
                .name(request.name())
                .description(request.description())
                .build();
        Project savedProject = projectRepository.save(project);

        ProjectMemberId projectMemberId = new ProjectMemberId(savedProject.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .projectMemberId(projectMemberId)
                .project(savedProject)
                .user(owner)
                .projectRole(ProjectRole.OWNER)
                .invitedAt(Instant.now())
                .joinedAt(Instant.now())
                .invitedBy(owner)
                .build();
        projectMemberRepository.save(projectMember);
        return projectMapper.toProjectResponse(savedProject);
    }

    @Override
    public Page<ProjectSummaryResponse> getLoggedInUserProjects(Long userId, Pageable pageable) {
        userExistence(userId);
        Page<ProjectWithRole> allProjects = projectRepository.findAllProjectsByUserId(userId, pageable);
        return allProjects
                .map(projectWithRole ->
                        projectMapper.toProjectSummaryResponse(projectWithRole.getProject(), projectWithRole.getRole()));
    }

    @Override
    public ProjectResponse getProjectDetailsByUserAndProjectId(Long id, Long userId) {
        Project project = projectRepository.findAccessibleProjectsByUserId(userId, id)
                .orElseThrow(() -> new NotActualOwnerOfProjectException("User with id: " + userId + " is not the owner of project with id: " + id));
        return projectMapper.toProjectResponse(project);
    }


    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotExistException("Project not exists with id: " + id));
        if (project.getName().equals(request.name()) && project.getDescription().equals(request.description())) {
            throw new SameProjectDetailsException("Project details is same as before. Please provide new details.");
        }
//        if (!project.getOwner().getId().equals(userId)) {
//            throw new NotActualOwnerOfProjectException("User with id: " + userId + " is not the actual owner of project with id: " + id);
//        }
        project.setName(request.name());
        project.setDescription(request.description());
        Project updatedProject = projectRepository.save(project);
        return projectMapper.toProjectResponse(updatedProject);
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotExistException("Project not exists with id: " + id));
//        if (!project.getOwner().getId().equals(userId)) {
//            throw new NotActualOwnerOfProjectException("User with id: " + userId + " is not the actual owner of project with id: " + id);
//        }
        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    private UserEntity userExistence(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotExistException("User not found with id: " + userId));
    }
}
