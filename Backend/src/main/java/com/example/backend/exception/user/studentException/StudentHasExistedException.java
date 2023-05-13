package com.example.backend.exception.user.studentException;

public class StudentHasExistedException extends RuntimeException {
    public StudentHasExistedException(String message) {
        super(message);
    }
}
