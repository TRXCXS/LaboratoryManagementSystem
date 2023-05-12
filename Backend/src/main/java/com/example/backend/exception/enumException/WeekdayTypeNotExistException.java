package com.example.backend.exception.enumException;

public class WeekdayTypeNotExistException extends RuntimeException {
    public WeekdayTypeNotExistException(String message) {
        super(message);
    }
}
