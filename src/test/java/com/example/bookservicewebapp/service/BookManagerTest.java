package com.example.bookservicewebapp.service;

import com.example.bookservicewebapp.TestUtils;
import com.example.bookservicewebapp.model.Author;
import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.repository.BookRepository;
import com.example.bookservicewebapp.service.BookManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookManagerTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookManager bookManager;

    @Test
    public void saveBookTest() {
        Book inputBook = new Book();
        Book resultBook = new Book();
        resultBook.setId(2000L);

        when(bookRepository.save(inputBook)).thenReturn(resultBook);

        Book saved = bookManager.saveBook(inputBook);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(2000L);
    }

    @Test
    public void buildBookTest() {
        String title = "Example Title";
        String isbn = "12345678-1-12";
        String forename = "forename";
        String surname = "surname";
        BookInput bookInput = TestUtils.getSimpleBookInput(surname, forename, title, isbn);

        Author author = new Author();
        author.setSurname(surname);
        author.setForename(forename);

        Book book = bookManager.buildBook(bookInput, author);

        assertThat(book).isNotNull();
        assertThat(book.getId()).isNull();
        assertThat(book.getIsbn()).isEqualTo(isbn);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getTitle()).isEqualTo(title);
    }

    @Test
    public void getAllBooksTest() {
        List<Book> all = new ArrayList<>();
        all.add(new Book());
        all.add(new Book());
        all.add(new Book());

        when(bookRepository.findAll()).thenReturn(all);

        List<Book> allBooks = bookManager.getAllBooks();

        assertThat(allBooks).hasSize(all.size()).isEqualTo(all);
    }
}
