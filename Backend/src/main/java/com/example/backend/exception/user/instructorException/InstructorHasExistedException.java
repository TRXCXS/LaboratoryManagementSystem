package com.example.backend.exception.user.instructorException;

public class InstructorHasExistedException extends RuntimeException {
    public InstructorHasExistedException(String message) {
        super(message);
    }
}
