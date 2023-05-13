package com.example.backend.exception.user.userRoleException;

public class UserRoleHasExistedException extends RuntimeException {
    public UserRoleHasExistedException(String message) {
        super(message);
    }
}
