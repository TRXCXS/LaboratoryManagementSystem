package com.example.backend.exception.request.instructorRequestException;

public class InstructorRequestNotExistException extends RuntimeException {
    public InstructorRequestNotExistException(String message) {
        super(message);
    }
}
