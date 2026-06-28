package com.niladri.lovable_app.service.project.impl;

import com.niladri.lovable_app.dto.request.project.ProjectRequest;
import com.niladri.lovable_app.dto.response.auth.UserProfileResponse;
import com.niladri.lovable_app.dto.response.project.ProjectResponse;
import com.niladri.lovable_app.dto.response.project.ProjectSummaryResponse;
import com.niladri.lovable_app.entity.Project;
import com.niladri.lovable_app.entity.UserEntity;
import com.niladri.lovable_app.exceptions.UserNotFoundException;
import com.niladri.lovable_app.mapper.ProjectMapper;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class ProjectService implements IProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectMapper projectMapper;

    @Override
    public Page<ProjectSummaryResponse> getUserProjects(Long userId, Pageable pageable) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Page<Project> allProjects = projectRepository.findAllProjectsByUserId(userId, pageable);
        return allProjects.map(projectMapper::toProjectSummaryResponse);
    }

    @Override
    public ProjectResponse getUserProjectById(Long id, Long userId) {
        Project project = projectRepository.findByOwnerId(userId).orElseThrow(() -> new RuntimeException("Project not found"));
        UserEntity ownerDetails = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfileResponse mappedUser = project.getOwner().map(ownerDetails);
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt(),
                project.getUpdatedAt(),
                mappedUser
        );
    }

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        Project project = Project.builder()
                .name(request.name())
                .description(request.description())
                .owner(user)
                .build();
        Project savedProject = projectRepository.save(project);
        return projectMapper.toProjectResponse(savedProject);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        return null;
    }

    @Override
    public void softDelete(Long id, Long userId) {

    }
}
