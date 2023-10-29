package com.example.stc.service.folder;

import com.example.stc.database.entity.Folder;
import com.example.stc.database.entity.ItemType;
import com.example.stc.database.entity.PermissionGroup;
import com.example.stc.database.repository.FolderRepository;
import com.example.stc.database.repository.PermissionGroupRepository;
import com.example.stc.service.exception.ResourceNotFoundException;
import com.example.stc.service.folder.dto.GetFolderResponse;
import com.example.stc.service.folder.dto.PostFolderRequest;
import com.example.stc.service.folder.dto.PostFolderResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final PermissionGroupRepository permissionGroupRepository;

    public FolderService(FolderRepository folderRepository, PermissionGroupRepository permissionGroupRepository) {
        this.folderRepository = folderRepository;
        this.permissionGroupRepository = permissionGroupRepository;
    }

    public GetFolderResponse getFolderById(Long id) {
        Folder folder = folderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        return new GetFolderResponse(folder.getId(), folder.getName(), folder.getPermissionGroup().getId());
    }

    public PostFolderResponse createNewFolder(PostFolderRequest request) {
        PermissionGroup permissionGroup = permissionGroupRepository
                .findById(request.permissionGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(request.permissionGroupId()));

        Folder parentFolder = folderRepository
                .findById(request.parentFolderId())
                .orElseThrow(() -> new ResourceNotFoundException(request.permissionGroupId()));

        Folder folder = new Folder();
        folder.setType(ItemType.FOLDER);
        folder.setName(request.name());
        folder.setPermissionGroup(permissionGroup);
        folder.setContainingFolder(parentFolder);

        Folder savedFolder = folderRepository.save(folder);
        return new PostFolderResponse(savedFolder.getId(), savedFolder.getName(), savedFolder.getPermissionGroup().getId());
    }

    public List<GetFolderResponse> getAllFoldersByContainingFolderId(Long id) {
        List<GetFolderResponse> folderResponses = new ArrayList<>();
        List<Folder> folders = folderRepository.findAllByTypeAndContainingFolderId(ItemType.FOLDER, id);
        folders.forEach(folder -> folderResponses.add(new GetFolderResponse(folder.getId(), folder.getName(), folder.getPermissionGroup().getId())));
        return folderResponses;
    }

}
