package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.TestUtils;
import com.example.bookservicewebapp.model.ApplicationErrors;
import com.example.bookservicewebapp.model.exception.AuthorNameValidationException;
import com.example.bookservicewebapp.model.exception.RequestDataValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookInputValidatorTest {

    private BookInputValidator bookInputValidator;

    @BeforeEach
    public void setup() {
        bookInputValidator = new BookInputValidator();
    }

    @Test
    public void validateTestWhenSurnameStartsWithA() {
        assertDoesNotThrow(() -> bookInputValidator.validate(TestUtils.getSimpleBookInput("Aaa", "Bbb")));
    }

    @Test
    public void validateTestWhenForenameStartsWithA() {
        assertDoesNotThrow(() -> bookInputValidator.validate(TestUtils.getSimpleBookInput("Bbb", "Aaa")));
    }

    @Test
    public void validateTestWhenForenameAndSurnameStartsWithA() {
        assertDoesNotThrow(() -> bookInputValidator.validate(TestUtils.getSimpleBookInput("Aaa", "Aaa")));
    }

    @Test
    public void validateTestWhenForenameAndSurnameDoesNotStartWithA() {
        assertThrows(AuthorNameValidationException.class,
                () -> bookInputValidator.validate(TestUtils.getSimpleBookInput("Baa", "Baa")),
                ApplicationErrors.FORENAME_SURNAME_VALIDATION_FAILED);
    }

    @Test
    public void validateTestWhenNull() {
        assertThrows(RequestDataValidationException.class,
                () -> bookInputValidator.validate(null),
                ApplicationErrors.BOOK_INPUT_VALIDATION_ERROR);
    }
}
