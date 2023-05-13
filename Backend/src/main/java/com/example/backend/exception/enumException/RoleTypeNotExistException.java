package com.example.backend.exception.enumException;

public class RoleTypeNotExistException extends RuntimeException {
    public RoleTypeNotExistException(String message) {
        super(message);
    }
}
