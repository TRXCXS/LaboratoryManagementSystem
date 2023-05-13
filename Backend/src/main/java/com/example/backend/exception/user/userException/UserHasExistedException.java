package com.example.backend.exception.user.userException;

public class UserHasExistedException extends RuntimeException {
    public UserHasExistedException(String message) {
        super(message);
    }
}
