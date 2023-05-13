package com.example.backend.exception.user.administratorException;

public class AdministratorHasExistedException extends RuntimeException {
    public AdministratorHasExistedException(String message) {
        super(message);
    }
}
