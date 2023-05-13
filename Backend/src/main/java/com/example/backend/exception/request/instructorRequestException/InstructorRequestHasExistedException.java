package com.example.backend.exception.request.instructorRequestException;

public class InstructorRequestHasExistedException extends RuntimeException {
    public InstructorRequestHasExistedException(String message) {
        super(message);
    }
}
