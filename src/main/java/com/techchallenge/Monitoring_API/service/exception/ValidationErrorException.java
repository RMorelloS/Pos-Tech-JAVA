package com.techchallenge.Monitoring_API.service.exception;

public class ValidationErrorException extends RuntimeException{
    public ValidationErrorException(String message){
        super(message);
    }
}
