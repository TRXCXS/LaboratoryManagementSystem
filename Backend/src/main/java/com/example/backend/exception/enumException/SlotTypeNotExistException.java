package com.example.backend.exception.enumException;

public class SlotTypeNotExistException extends RuntimeException {
    public SlotTypeNotExistException(String message) {
        super(message);
    }
}
