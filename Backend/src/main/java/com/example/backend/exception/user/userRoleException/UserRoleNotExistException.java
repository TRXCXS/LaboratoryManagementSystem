package com.example.backend.exception.user.userRoleException;

public class UserRoleNotExistException extends RuntimeException {
    public UserRoleNotExistException(String message) {
        super(message);
    }
}
