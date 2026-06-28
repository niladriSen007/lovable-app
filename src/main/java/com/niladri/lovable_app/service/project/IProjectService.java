package com.niladri.lovable_app.service.project;

import com.niladri.lovable_app.dto.request.project.ProjectRequest;
import com.niladri.lovable_app.dto.response.project.ProjectResponse;
import com.niladri.lovable_app.dto.response.project.ProjectSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProjectService {
    Page<ProjectSummaryResponse> getUserProjects(Long userId, Pageable pageable);

    ProjectResponse getUserProjectById(Long id, Long userId);

    ProjectResponse createProject(ProjectRequest request, Long userId);

    ProjectResponse updateProject(Long id, ProjectRequest request, Long userId);

    void softDelete(Long id, Long userId);
}
