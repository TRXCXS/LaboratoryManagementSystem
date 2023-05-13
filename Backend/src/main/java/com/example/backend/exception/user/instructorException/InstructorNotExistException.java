package com.example.backend.exception.user.instructorException;

public class InstructorNotExistException extends RuntimeException {
    public InstructorNotExistException(String message) {
        super(message);
    }
}
