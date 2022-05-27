package com.example.bookservicewebapp.gui.view;

import com.example.bookservicewebapp.gui.view.formpage.BookForm;
import com.example.bookservicewebapp.model.BookInput;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookFormTest {
    @Test
    public void saveEventHasCorrectValues() {
        BookForm form = new BookForm();
        form.init();

        String forename = "Abcd";
        String surname = "Abc";
        String title = "title";
        String isbn = "123456789-1-3";

        form.getAuthorForename().setValue(forename);
        form.getAuthorSurname().setValue(surname);
        form.getTitle().setValue(title);
        form.getIsbn().setValue(isbn);

        AtomicReference<BookInput> savedBookRef = new AtomicReference<>(null);
        form.addListener(BookForm.SaveEvent.class, e -> {
            savedBookRef.set(e.getBookInput());
        });
        form.getSave().click();

        BookInput bookInput = savedBookRef.get();

        assertEquals(isbn, bookInput.getIsbn());
        assertEquals(title, bookInput.getTitle());
        assertEquals(forename, bookInput.getAuthorForename());
        assertEquals(surname, bookInput.getAuthorSurname());
    }

    @Test
    public void setInvalidAuthorFieldsTest() {
        BookForm form = new BookForm();
        form.init();

        form.getAuthorSurname().setErrorMessage("Error message1");
        form.getAuthorSurname().setInvalid(false);
        form.getAuthorForename().setErrorMessage("Error message2");
        form.getAuthorForename().setInvalid(false);

        form.setInvalidAuthorFields();

        assertTrue(form.getAuthorSurname().isInvalid());
        assertTrue(form.getAuthorForename().isInvalid());
        assertThat(form.getAuthorSurname().getErrorMessage()).isEmpty();
        assertThat(form.getAuthorForename().getErrorMessage()).isEmpty();
    }

    @Test
    public void cancelEventTest() {
        BookForm form = new BookForm();
        form.init();

        AtomicReference<BookInput> savedBookRef = new AtomicReference<>(null);
        form.addListener(BookForm.CancelEvent.class, e -> {
            savedBookRef.set(null);
        });
        form.getCancel().click();

        BookInput bookInput = savedBookRef.get();

        assertNull(bookInput);
    }

    @Test
    public void initTest() {
        BookForm form = new BookForm();

        assertNull(form.getSave());
        assertNull(form.getCancel());
        assertNull(form.getTitle());
        assertNull(form.getIsbn());
        assertNull(form.getAuthorSurname());
        assertNull(form.getAuthorForename());
        assertNull(form.getBinder());
        assertNull(form.getTitleLabel());

        form.init();

        assertNotNull(form.getSave());
        assertNotNull(form.getCancel());
        assertNotNull(form.getTitle());
        assertNotNull(form.getIsbn());
        assertNotNull(form.getAuthorSurname());
        assertNotNull(form.getAuthorForename());
        assertNotNull(form.getBinder());
        assertNotNull(form.getTitleLabel());
    }
}
