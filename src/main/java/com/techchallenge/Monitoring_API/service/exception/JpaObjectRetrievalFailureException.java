package com.techchallenge.Monitoring_API.service.exception;

public class JpaObjectRetrievalFailureException extends RuntimeException{
    public JpaObjectRetrievalFailureException(String message){
        super(message);
    }
}
