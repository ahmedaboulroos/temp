package com.example.stc.service.space;

import com.example.stc.database.entity.Folder;
import com.example.stc.database.entity.ItemType;
import com.example.stc.database.entity.PermissionGroup;
import com.example.stc.database.repository.FolderRepository;
import com.example.stc.database.repository.PermissionGroupRepository;
import com.example.stc.service.exception.ResourceNotFoundException;
import com.example.stc.service.space.dto.CreateSpaceRequest;
import com.example.stc.service.space.dto.CreateSpaceResponse;
import com.example.stc.service.space.dto.GetSpaceResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpaceService {

    private final FolderRepository folderRepository;
    private final PermissionGroupRepository permissionGroupRepository;

    public SpaceService(FolderRepository folderRepository, PermissionGroupRepository permissionGroupRepository) {
        this.folderRepository = folderRepository;
        this.permissionGroupRepository = permissionGroupRepository;
    }

    public List<GetSpaceResponse> getAllSpaces() {
        List<GetSpaceResponse> spaceResponseList = new ArrayList<>();
        List<Folder> spaces = folderRepository.findAllByType(ItemType.SPACE);
        spaces.forEach(space -> spaceResponseList.add(new GetSpaceResponse(space.getId(), space.getName())));
        return spaceResponseList;
    }

    public GetSpaceResponse getSpaceById(Long id) {
        Folder folder = folderRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return new GetSpaceResponse(folder.getId(), folder.getName());
    }

    public CreateSpaceResponse createSpace(CreateSpaceRequest request) {

        PermissionGroup group = permissionGroupRepository
                .findById(request.permissionGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(request.permissionGroupId()));

        Folder folder = new Folder();
        folder.setType(ItemType.SPACE);
        folder.setName(request.name());
        folder.setContainingFolder(null);
        folder.setPermissionGroup(group);

        Folder savedFolder = folderRepository.save(folder);
        return new CreateSpaceResponse(savedFolder.getId(), savedFolder.getName(), savedFolder.getPermissionGroup().getId());
    }

}
