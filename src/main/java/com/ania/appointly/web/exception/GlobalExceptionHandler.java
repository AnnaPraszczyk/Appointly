package com.ania.appointly.web.exception;
import com.ania.appointly.domain.exeptions.UserValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<String> handleUserValidation(UserValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
