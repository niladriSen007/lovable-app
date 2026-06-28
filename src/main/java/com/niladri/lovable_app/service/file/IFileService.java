package com.niladri.lovable_app.service.file;

import com.niladri.lovable_app.dto.response.file.FileContentResponse;
import com.niladri.lovable_app.dto.response.file.FileNode;

import java.util.List;

public interface IFileService {
    List<FileNode> getFileTree(Long projectId, Long userId);

    FileContentResponse getFileContent(Long projectId, String path, Long userId);
}
