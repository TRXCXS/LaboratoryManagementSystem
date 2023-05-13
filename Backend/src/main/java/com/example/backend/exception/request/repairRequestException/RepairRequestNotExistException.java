package com.example.backend.exception.request.repairRequestException;

public class RepairRequestNotExistException extends RuntimeException {
    public RepairRequestNotExistException(String message) {
        super(message);
    }
}
