package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.model.Author;
import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookManager {
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        List<Book> all = bookRepository.findAll();
        log.info(String.format("%d books were received from the database", all.size()));

        return all;
    }

    public Book saveBook(Book book) {
        Book saved = bookRepository.save(book);
        log.info(String.format("Book with ID=%d was successfully created", saved.getId()));

        return saved;
    }

    public Book buildBook(BookInput bookInput, Author author) {
        Book book = new Book();
        book.setIsbn(bookInput.getIsbn());
        book.setTitle(bookInput.getTitle());
        book.setAuthor(author);

        return book;
    }
}
