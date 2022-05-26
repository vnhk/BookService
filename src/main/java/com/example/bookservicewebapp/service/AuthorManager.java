package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.model.Author;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorManager {
    private final AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        Author saved = authorRepository.save(author);
        log.info(String.format("Author with ID=%d was successfully created", saved.getId()));

        return saved;
    }

    public Author buildAuthor(BookInput bookInput) {
        Author author = new Author();
        author.setForename(bookInput.getAuthorForename());
        author.setSurname(bookInput.getAuthorSurname());

        return author;
    }
}
