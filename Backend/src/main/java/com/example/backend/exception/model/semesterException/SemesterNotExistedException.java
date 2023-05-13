package com.example.backend.exception.model.semesterException;

public class SemesterNotExistedException extends RuntimeException {
    public SemesterNotExistedException(String message) {
        super(message);
    }
}
