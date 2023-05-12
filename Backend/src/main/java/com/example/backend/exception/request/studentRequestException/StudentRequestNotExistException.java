package com.example.backend.exception.request.studentRequestException;

public class StudentRequestNotExistException extends RuntimeException {
    public StudentRequestNotExistException(String message) {
        super(message);
    }
}
