package com.techchallenge.Monitoring_API.service.exception;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message){
        super(message);
    }
}
