package com.example.stc.service.file.dto;

public record UploadFileRequest(Long fileId, byte[] fileData, String originalFilename, String contentType) {
}
