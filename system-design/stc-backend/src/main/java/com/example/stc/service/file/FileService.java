package com.example.stc.service.file;

import com.example.stc.database.entity.File;
import com.example.stc.database.entity.Folder;
import com.example.stc.database.entity.ItemType;
import com.example.stc.database.entity.PermissionGroup;
import com.example.stc.database.repository.FileRepository;
import com.example.stc.database.repository.FolderRepository;
import com.example.stc.database.repository.PermissionGroupRepository;
import com.example.stc.service.exception.ResourceNotFoundException;
import com.example.stc.service.file.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final PermissionGroupRepository permissionGroupRepository;

    public FileService(FileRepository fileRepository, FolderRepository folderRepository, PermissionGroupRepository permissionGroupRepository) {
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.permissionGroupRepository = permissionGroupRepository;
    }

    public List<GetFileResponse> getAllFilesInFolderById(Long folderId) {
        List<GetFileResponse> response = new ArrayList<>();
        List<File> files = fileRepository.findAllByContainingFolderId(folderId);
        files.forEach(file -> response.add(new GetFileResponse(file.getId(), file.getName())));
        return response;
    }

    public GetFileResponse getFileById(Long id) {
        File file = fileRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return new GetFileResponse(file.getId(), file.getName());
    }

    public PostFileResponse createFile(PostFileRequest request) {
        PermissionGroup permissionGroup = permissionGroupRepository
                .findById(request.permissionGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(request.permissionGroupId()));

        Folder containingFolder = folderRepository
                .findById(request.folderId())
                .orElseThrow(() -> new ResourceNotFoundException(request.folderId()));

        File file = new File();
        file.setType(ItemType.FILE);
        file.setName("Not Uploaded");
        file.setContainingFolder(containingFolder);
        file.setPermissionGroup(permissionGroup);
        File savedFile = fileRepository.save(file);
        return new PostFileResponse(savedFile.getId(), savedFile.getContainingFolder().getId(), savedFile.getPermissionGroup().getId());
    }

    public void uploadFile(UploadFileRequest uploadFileRequest) {
        File savedFile = fileRepository
                .findById(uploadFileRequest.fileId())
                .orElseThrow(() -> new ResourceNotFoundException(uploadFileRequest.fileId()));

        savedFile.setBinary(uploadFileRequest.fileData());
        savedFile.setName(uploadFileRequest.originalFilename());
        savedFile.setContentType(uploadFileRequest.contentType());
        fileRepository.save(savedFile);
    }

    public DownloadFileResponse downloadFile(Long fileId) {
        File savedFile = fileRepository
                .findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException(fileId));

        return new DownloadFileResponse(savedFile.getId(), savedFile.getBinary(), savedFile.getName(), savedFile.getContentType());
    }

}
