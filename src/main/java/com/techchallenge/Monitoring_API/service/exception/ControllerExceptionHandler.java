package com.techchallenge.Monitoring_API.service.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.http.ResponseEntity;

import javax.swing.text.html.parser.Entity;
import java.time.Instant;
@ControllerAdvice
public class ControllerExceptionHandler {
    private DefaultError error = new DefaultError();
    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<DefaultError> entityNotFound(ControllerNotFoundException exception,
            HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setMessage(exception.getMessage());
        error.setError("Objeto não encontrado");
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
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DefaultError> entityNotFound(ValidationException exception, HttpServletRequest request){
        HttpStatus status =HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setError("Erro na validação de campos");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<DefaultError> jpaError(JpaObjectRetrievalFailureException exception, HttpServletRequest request){
        HttpStatus status =HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setError("Erro de integridade");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<DefaultError> jpaError(EntityNotFoundException exception, HttpServletRequest request){
        HttpStatus status =HttpStatus.BAD_REQUEST;
        error.setTimestamp(Instant.now());
        error.setError("Erro de integridade na inserção");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

}
