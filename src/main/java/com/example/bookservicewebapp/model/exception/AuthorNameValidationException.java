package com.example.bookservicewebapp.model.exception;

public class AuthorNameValidationException extends RequestDataValidationException {
    public AuthorNameValidationException(String message) {
        super(message);
    }
}
