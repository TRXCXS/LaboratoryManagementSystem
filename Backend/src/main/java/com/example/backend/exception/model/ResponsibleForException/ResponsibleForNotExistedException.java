package com.example.backend.exception.model.ResponsibleForException;

public class ResponsibleForNotExistedException extends RuntimeException {
    public ResponsibleForNotExistedException(String message) {
        super(message);
    }
}
