package com.example.stc.controller;

import com.example.stc.service.file.FileService;
import com.example.stc.service.file.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public List<GetFileResponse> getAllFiles(@RequestParam Long folderId) {
        return fileService.getAllFilesInFolderById(folderId);
    }

    @GetMapping("/files/{id}")
    public GetFileResponse getFileById(@PathVariable Long id) {
        return fileService.getFileById(id);
    }

    @PostMapping("/files")
    public PostFileResponse addNewFile(@RequestBody PostFileRequest request) {
        return  fileService.createFile(request);
    }

    @PostMapping("/files/{id}/upload")
    public void uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();
        fileService.uploadFile(new UploadFileRequest(id, bytes, originalFilename, contentType));
    }

    @GetMapping("/files/{id}/download")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws IOException {
        DownloadFileResponse downloadFileResponse = fileService.downloadFile(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(downloadFileResponse.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + downloadFileResponse.originalFilename())
                .body(downloadFileResponse.fileData());
    }

}
