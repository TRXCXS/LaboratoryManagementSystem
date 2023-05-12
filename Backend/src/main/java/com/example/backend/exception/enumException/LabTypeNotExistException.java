package com.example.backend.exception.enumException;

public class LabTypeNotExistException extends RuntimeException {
    public LabTypeNotExistException(String message) {
        super(message);
    }
}
