package com.example.bookservicewebapp.model.exception;

public class RequestDataValidationException extends BookApplicationException {
    public RequestDataValidationException(String message) {
        super(message);
    }
}
