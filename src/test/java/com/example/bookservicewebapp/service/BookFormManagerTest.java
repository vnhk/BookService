package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.TestUtils;
import com.example.bookservicewebapp.model.ApplicationErrors;
import com.example.bookservicewebapp.model.Author;
import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.model.exception.DatabaseOperationException;
import com.example.bookservicewebapp.service.AuthorManager;
import com.example.bookservicewebapp.service.BookFormManager;
import com.example.bookservicewebapp.service.BookInputValidator;
import com.example.bookservicewebapp.service.BookManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookFormManagerTest {
    @Mock
    private BookInputValidator bookInputValidator;

    @Mock
    private AuthorManager authorManager;

    @Mock
    private BookManager bookManager;

    @InjectMocks
    private BookFormManager bookFormManager;

    private Author author;

    private Book book;

    private BookInput bookInput;

    @BeforeEach
    public void setup() {
        author = new Author();
        book = new Book();
        bookInput = TestUtils.getSimpleBookInput("Surname", "Forename");

        when(authorManager.buildAuthor(bookInput)).thenReturn(author);
    }

    @Test
    public void persistFormTestWhenAuthorCannotBeSaved() {
        when(authorManager.saveAuthor(author)).thenThrow(RuntimeException.class);

        assertThrows(DatabaseOperationException.class,
                () -> bookFormManager.persistForm(bookInput), ApplicationErrors.CANNOT_SAVE_AUTHOR);
    }

    @Test
    public void persistFormTestWhenBookCannotBeSaved() {
        when(authorManager.buildAuthor(bookInput)).thenReturn(author);
        when(authorManager.saveAuthor(author)).thenReturn(author);
        when(bookManager.buildBook(bookInput, author)).thenReturn(book);
        when(bookManager.saveBook(book)).thenThrow(RuntimeException.class);

        assertThrows(DatabaseOperationException.class,
                () -> bookFormManager.persistForm(bookInput), ApplicationErrors.CANNOT_SAVE_BOOK);
    }

    @Test
    public void persistFormTestWhenSuccess() {
        when(authorManager.buildAuthor(bookInput)).thenReturn(author);
        when(authorManager.saveAuthor(author)).thenReturn(author);
        when(bookManager.buildBook(bookInput, author)).thenReturn(book);
        when(bookManager.saveBook(book)).thenReturn(book);

        assertDoesNotThrow(() -> bookFormManager.persistForm(bookInput));
    }
}
