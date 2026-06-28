package com.niladri.lovable_app.service.file.impl;

import com.niladri.lovable_app.dto.response.file.FileContentResponse;
import com.niladri.lovable_app.dto.response.file.FileNode;
import com.niladri.lovable_app.service.file.IFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements IFileService {
    @Override
    public List<FileNode> getFileTree(Long projectId, Long userId) {
        return List.of();
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path, Long userId) {
        return null;
    }
}
