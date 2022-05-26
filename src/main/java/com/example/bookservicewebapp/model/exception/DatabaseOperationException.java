package com.example.bookservicewebapp.model.exception;

public class DatabaseOperationException extends BookApplicationException {
    public DatabaseOperationException(String message) {
        super(message);
    }
}
