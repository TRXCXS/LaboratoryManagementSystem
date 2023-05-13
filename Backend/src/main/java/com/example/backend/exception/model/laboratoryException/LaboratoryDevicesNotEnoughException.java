package com.example.backend.exception.model.laboratoryException;

public class LaboratoryDevicesNotEnoughException extends RuntimeException {
    public LaboratoryDevicesNotEnoughException(String message) {
        super(message);
    }
}
