package com.example.backend.exception.user.userException;

public class NoUserRoleException extends RuntimeException {
    public NoUserRoleException(String message) {
        super(message);
    }
}
