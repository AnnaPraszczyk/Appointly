package com.ania.appointly.domain.exeptions;

public class ServiceValidationException extends RuntimeException {
    public ServiceValidationException(String message) {
        super(message);
    }
}
