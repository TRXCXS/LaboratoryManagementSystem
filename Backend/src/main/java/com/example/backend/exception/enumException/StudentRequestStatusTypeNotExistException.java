package com.example.backend.exception.enumException;

public class StudentRequestStatusTypeNotExistException extends RuntimeException {
    public StudentRequestStatusTypeNotExistException(String message) {
        super(message);
    }
}
