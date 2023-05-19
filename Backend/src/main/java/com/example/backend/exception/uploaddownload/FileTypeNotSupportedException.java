package com.example.backend.exception.uploaddownload;

public class FileTypeNotSupportedException extends RuntimeException {
    public FileTypeNotSupportedException(String message) {
        super(message);
    }
}
