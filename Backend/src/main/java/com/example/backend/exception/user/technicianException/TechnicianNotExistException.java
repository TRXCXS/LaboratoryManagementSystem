package com.example.backend.exception.user.technicianException;

public class TechnicianNotExistException extends RuntimeException {
    public TechnicianNotExistException(String message) {
        super(message);
    }
}
