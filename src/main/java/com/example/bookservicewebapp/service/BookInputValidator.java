package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.model.ApplicationErrors;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.model.exception.AuthorNameValidationException;
import com.example.bookservicewebapp.model.exception.RequestDataValidationException;
import com.example.bookservicewebapp.utils.ValidatorHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookInputValidator {

    private final static String CAPITAL_CHAR_A = "A";

    public void validate(BookInput bookInput) {
        try {
            validateAuthor(bookInput);
        } catch (RequestDataValidationException e) {
            throw e;
        } catch (Exception e) {
            log.error(ApplicationErrors.BOOK_INPUT_VALIDATION_ERROR, e);
            throw new RequestDataValidationException(ApplicationErrors.BOOK_INPUT_VALIDATION_ERROR);
        }
    }

    private void validateAuthor(BookInput bookInput) {
        if (!doesForenameOrSurnameStartsWithA(bookInput)) {
            log.error(ApplicationErrors.FORENAME_SURNAME_VALIDATION_FAILED);
            throw new AuthorNameValidationException(ApplicationErrors.FORENAME_SURNAME_VALIDATION_FAILED);
        }
    }

    private boolean doesForenameOrSurnameStartsWithA(BookInput bookInput) {
        return ValidatorHelper.doesTextStartsWith(bookInput.getAuthorSurname(), CAPITAL_CHAR_A)
                || ValidatorHelper.doesTextStartsWith(bookInput.getAuthorForename(), CAPITAL_CHAR_A);
    }
}
