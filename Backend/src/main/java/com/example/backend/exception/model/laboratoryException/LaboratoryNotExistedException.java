package com.example.backend.exception.model.laboratoryException;

public class LaboratoryNotExistedException extends RuntimeException {
    public LaboratoryNotExistedException(String message) {
        super(message);
    }
}
