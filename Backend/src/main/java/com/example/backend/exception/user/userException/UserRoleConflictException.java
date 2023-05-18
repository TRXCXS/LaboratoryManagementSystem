package com.example.backend.exception.user.userException;

public class UserRoleConflictException extends RuntimeException {
    public UserRoleConflictException(String message) {
        super(message);
    }
}
