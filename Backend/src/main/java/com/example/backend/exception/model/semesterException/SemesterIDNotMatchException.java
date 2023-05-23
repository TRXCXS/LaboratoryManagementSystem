package com.example.backend.exception.model.semesterException;

public class SemesterIDNotMatchException extends RuntimeException {
    public SemesterIDNotMatchException(String message) {
        super(message);
    }
}
