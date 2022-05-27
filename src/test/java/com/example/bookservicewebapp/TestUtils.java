package com.example.bookservicewebapp;

import com.example.bookservicewebapp.model.BookInput;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {
    public static BookInput getSimpleBookInput(String surname, String forename) {
        BookInput bookInput = new BookInput();
        bookInput.setAuthorSurname(surname);
        bookInput.setAuthorForename(forename);

        return bookInput;
    }

    public static BookInput getSimpleBookInput(String surname, String forename, String title) {
        BookInput bookInput = getSimpleBookInput(surname, forename);
        bookInput.setTitle(title);

        return bookInput;
    }

    public static BookInput getSimpleBookInput(String surname, String forename, String title, String isbn) {
        BookInput bookInput = getSimpleBookInput(surname, forename, title);
        bookInput.setIsbn(isbn);

        return bookInput;
    }
}
