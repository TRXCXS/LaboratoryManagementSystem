package com.example.backend.exception.user.studentException;

public class StudentNotExistException extends RuntimeException {
    public StudentNotExistException(String message) {
        super(message);
    }
}
