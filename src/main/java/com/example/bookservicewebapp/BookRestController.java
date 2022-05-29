package com.example.bookservicewebapp;

import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.service.BookFormManager;
import com.example.bookservicewebapp.service.BookManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookFormManager bookFormManager;
    private final BookManager bookManager;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookManager.getAllBooks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addBook(@Valid @RequestBody BookInput bookInput) {
        bookFormManager.persistForm(bookInput);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
