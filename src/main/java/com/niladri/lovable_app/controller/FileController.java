package com.niladri.lovable_app.controller;

import com.niladri.lovable_app.dto.response.ApiResponse;
import com.niladri.lovable_app.dto.response.file.FileContentResponse;
import com.niladri.lovable_app.dto.response.file.FileNode;
import com.niladri.lovable_app.service.file.IFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects/{projectId}/files")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    IFileService fileService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FileNode>>> getFileTree(@PathVariable Long projectId) {
        Long userId = 1L;
        return ResponseEntity.ok(ApiResponse.success(fileService.getFileTree(projectId, userId), HttpStatus.OK.value()));
    }

    @GetMapping("/{*path}") // /src/hooks/get-user-hook.jsx
    public ResponseEntity<ApiResponse<FileContentResponse>> getFile(
            @PathVariable Long projectId,
            @PathVariable String path
    ) {
        Long userId = 1L;
        return ResponseEntity.ok(ApiResponse.success(fileService.getFileContent(projectId, path, userId), HttpStatus.OK.value()));
    }

}
