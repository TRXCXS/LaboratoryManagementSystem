package com.example.backend.exception.user.technicianException;

public class TechnicianHasExistedException extends RuntimeException {
    public TechnicianHasExistedException(String message) {
        super(message);
    }
}
