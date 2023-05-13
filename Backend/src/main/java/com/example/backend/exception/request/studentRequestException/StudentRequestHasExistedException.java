package com.example.backend.exception.request.studentRequestException;

public class StudentRequestHasExistedException extends RuntimeException {
    public StudentRequestHasExistedException(String message) {
        super(message);
    }
}
