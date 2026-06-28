package com.niladri.lovable_app.controller;

import com.niladri.lovable_app.dto.request.project.ProjectRequest;
import com.niladri.lovable_app.dto.response.ApiResponse;
import com.niladri.lovable_app.dto.response.project.ProjectResponse;
import com.niladri.lovable_app.dto.response.project.ProjectSummaryResponse;
import com.niladri.lovable_app.service.project.IProjectService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProjectController {

    IProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProjectSummaryResponse>>> getMyProjects(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        Long userId = 1L; //TODO: update later with real Spring Security
        return ResponseEntity.ok(
                ApiResponse.success(projectService.getUserProjects(userId,pageable),HttpStatus.OK.value())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable Long id) {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(projectService.getUserProjectById(id, userId), HttpStatus.OK.value())
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@RequestBody @Valid ProjectRequest request) {
        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(projectService.createProject(request, userId), HttpStatus.CREATED.value())
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(@PathVariable Long id, @RequestBody ProjectRequest request) {
        Long userId = 1L;
        return ResponseEntity.ok(
                ApiResponse.success(projectService.updateProject(id, request, userId), HttpStatus.OK.value())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        Long userId = 1L;
        projectService.softDelete(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success(null, HttpStatus.NO_CONTENT.value())
        );
    }
}
