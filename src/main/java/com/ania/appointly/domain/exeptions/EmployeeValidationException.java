package com.ania.appointly.domain.exeptions;

public class EmployeeValidationException extends RuntimeException {
    public EmployeeValidationException(String message) {
        super(message);
    }
}
