package com.example.backend.exception.request.repairRequestException;

public class RepairRequestHasExistedException extends RuntimeException {
    public RepairRequestHasExistedException(String message) {
        super(message);
    }
}
