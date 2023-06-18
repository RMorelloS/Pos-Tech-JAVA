package com.techchallenge.Monitoring_API.service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ControllerExceptionHandler {
    private DefaultError error = new DefaultError();
    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(ControllerNotFoundException exception,
            HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<DefaultError> entityNotFound(DatabaseException exception, HttpServletRequest request){
        HttpStatus status =HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setError("Database error");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<DefaultError> entityNotFound(ValidationErrorException exception, HttpServletRequest request){
        HttpStatus status =HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setError(exception.getMessage());
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
