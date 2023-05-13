package com.example.backend.exception.enumException;

public class RepairRequestStatusTypeNotExistException extends RuntimeException {
    public RepairRequestStatusTypeNotExistException(String message) {
        super(message);
    }
}
