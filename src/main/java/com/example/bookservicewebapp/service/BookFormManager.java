package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.model.ApplicationErrors;
import com.example.bookservicewebapp.model.Author;
import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.model.exception.DatabaseOperationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookFormManager {
    private final BookInputValidator bookInputValidator;
    private final AuthorManager authorManager;
    private final BookManager bookManager;

    public void persistForm(BookInput bookInput) {
        bookInputValidator.validate(bookInput);
        Author author = saveAuthor(bookInput);
        saveBook(bookInput, author);
    }

    private Author saveAuthor(BookInput bookInput) {
        try {
            Author author = authorManager.buildAuthor(bookInput);
            return authorManager.saveAuthor(author);
        } catch (Exception e) {
            log.error(ApplicationErrors.CANNOT_SAVE_AUTHOR, e);
            throw new DatabaseOperationException(ApplicationErrors.CANNOT_SAVE_AUTHOR);
        }
    }

    private Book saveBook(BookInput bookInput, Author author) {
        try {
            Book book = bookManager.buildBook(bookInput, author);
            return bookManager.saveBook(book);
        } catch (Exception e) {
            log.error(ApplicationErrors.CANNOT_SAVE_BOOK, e);
            throw new DatabaseOperationException(ApplicationErrors.CANNOT_SAVE_BOOK);
        }
    }
}
