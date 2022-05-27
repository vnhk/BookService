package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.TestUtils;
import com.example.bookservicewebapp.model.Author;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorManagerTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorManager authorManager;

    @Test
    public void saveAuthorTest() {
        Author inputAuthor = new Author();
        Author resultAuthor = new Author();
        resultAuthor.setId(1000L);

        when(authorRepository.save(inputAuthor)).thenReturn(resultAuthor);

        Author saved = authorManager.saveAuthor(inputAuthor);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(1000L);
    }

    @Test
    public void buildAuthorTest() {
        String surname = "Surname";
        String forename = "Forename";

        BookInput bookInput = TestUtils.getSimpleBookInput(surname, forename);

        Author author = authorManager.buildAuthor(bookInput);

        assertThat(author).isNotNull();
        assertThat(author.getId()).isNull();
        assertThat(author.getBooks()).isNullOrEmpty();
        assertThat(author.getForename()).isEqualTo(forename);
        assertThat(author.getSurname()).isEqualTo(surname);
    }
}
