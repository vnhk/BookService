package com.example.bookservicewebapp;

import com.example.bookservicewebapp.model.exception.AuthorNameValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.RequestDispatcher;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final DefaultErrorAttributes defaultErrorAttributes;

    @ExceptionHandler(value = {AuthorNameValidationException.class})
    protected ResponseEntity<Object> handleAuthorNameValidationError(AuthorNameValidationException ex,
                                                                     WebRequest request) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value(), RequestAttributes.SCOPE_REQUEST);
        final Map<String, Object> errorAttributes = defaultErrorAttributes.getErrorAttributes(request, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE));

        return handleExceptionInternal(ex, errorAttributes, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
