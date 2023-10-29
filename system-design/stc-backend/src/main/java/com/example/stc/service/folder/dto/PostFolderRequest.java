package com.example.stc.service.folder.dto;

public record PostFolderRequest(String name, Long parentFolderId, Long permissionGroupId) {
}
