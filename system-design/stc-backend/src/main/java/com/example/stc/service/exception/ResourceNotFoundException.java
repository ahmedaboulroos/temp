package com.example.stc.service.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long id) {
        super("Resource with ID: " + id + " Not Found.");
    }
}
