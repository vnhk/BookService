package com.example.bookservicewebapp.gui.view;

import com.example.bookservicewebapp.gui.view.formpage.BookFormPage;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.model.exception.AuthorNameValidationException;
import com.example.bookservicewebapp.model.exception.BookApplicationException;
import com.example.bookservicewebapp.service.BookFormManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookFormPageTest {
    @Mock
    private BookFormManager bookManager;

    @Mock
    private Binder<BookInput> binder;

    @Mock
    private Notification successNotification;

    @Mock
    private Notification errorNotification;

    private BookFormPage bookFormPage;

    @BeforeEach
    public void setup() {
        bookFormPage = new BookFormPage(bookManager);
        ReflectionTestUtils.invokeMethod(bookFormPage, "initNotifications");
        ReflectionTestUtils.invokeMethod(bookFormPage, "init");
        ReflectionTestUtils.setField(bookFormPage.getBookForm(), "binder", binder);
        bookFormPage.errorNotification = errorNotification;
        bookFormPage.successNotification = successNotification;
    }

    @Test
    public void saveBookTestSuccess() {
        UI.setCurrent(mock(UI.class));

        when(binder.isValid()).thenReturn(true);

        bookFormPage.getBookForm().getSave().click();

        verify(bookManager).persistForm(any(BookInput.class));
        verify(successNotification).open();
    }

    @Test
    public void saveBookTestWhenNotValidForm() {
        when(binder.isValid()).thenReturn(false);

        bookFormPage.getBookForm().getSave().click();

        verify(bookManager, never()).persistForm(any(BookInput.class));
    }

    @Test
    public void saveBookTestAuthorNameException() {
        when(binder.isValid()).thenReturn(true);
        doThrow(AuthorNameValidationException.class)
                .when(bookManager).persistForm(any(BookInput.class));

        bookFormPage.getBookForm().getSave().click();

        verify(bookManager).persistForm(any(BookInput.class));
        verify(errorNotification).open();

        assertTrue(bookFormPage.getBookForm().getAuthorForename().isInvalid());
        assertTrue(bookFormPage.getBookForm().getAuthorSurname().isInvalid());
    }

    @Test
    public void saveBookTestGeneralApplicationException() {
        when(binder.isValid()).thenReturn(true);
        doThrow(BookApplicationException.class)
                .when(bookManager).persistForm(any(BookInput.class));

        bookFormPage.getBookForm().getSave().click();

        verify(bookManager).persistForm(any(BookInput.class));
        verify(errorNotification).open();

        assertFalse(bookFormPage.getBookForm().getAuthorForename().isInvalid());
        assertFalse(bookFormPage.getBookForm().getAuthorSurname().isInvalid());
    }
}
