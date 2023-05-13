package com.example.backend.exception.enumException;

public class SeasonTypeNotExistException extends RuntimeException {
    public SeasonTypeNotExistException(String message) {
        super(message);
    }
}
