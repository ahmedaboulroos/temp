package com.example.stc.controller;

import com.example.stc.service.folder.FolderService;
import com.example.stc.service.folder.dto.GetFolderResponse;
import com.example.stc.service.folder.dto.PostFolderRequest;
import com.example.stc.service.folder.dto.PostFolderResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @GetMapping("/folders")
    public List<GetFolderResponse> getAllFolders(@RequestParam Long containingFolderId) {
        return folderService.getAllFoldersByContainingFolderId(containingFolderId);
    }

    @GetMapping("/folders/{id}")
    public GetFolderResponse getFolderById(@PathVariable Long id) {
        return folderService.getFolderById(id);
    }

    @PostMapping("/folders")
    public PostFolderResponse createNewFolder(@RequestBody PostFolderRequest request) {
        return folderService.createNewFolder(request);
    }

}
