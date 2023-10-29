package com.example.stc.service.file.dto;

public record DownloadFileResponse(Long fileId, byte[] fileData, String originalFilename, String contentType) {
}
