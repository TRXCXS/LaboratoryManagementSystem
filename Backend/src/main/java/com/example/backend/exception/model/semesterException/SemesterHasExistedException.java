package com.example.backend.exception.model.semesterException;

public class SemesterHasExistedException extends RuntimeException {
    public SemesterHasExistedException(String message) {
        super(message);
    }
}
