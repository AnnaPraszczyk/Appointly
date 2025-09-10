package com.ania.appointly.domain.exeptions;

public class CompanyValidationException extends RuntimeException {
    public CompanyValidationException(String message) {
        super(message);
    }
}
