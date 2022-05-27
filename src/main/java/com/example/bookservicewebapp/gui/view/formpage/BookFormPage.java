package com.example.bookservicewebapp.gui.view.formpage;

import com.example.bookservicewebapp.gui.view.BookTemplatePage;
import com.example.bookservicewebapp.model.BookInput;
import com.example.bookservicewebapp.model.exception.AuthorNameValidationException;
import com.example.bookservicewebapp.model.exception.BookApplicationException;
import com.example.bookservicewebapp.service.BookFormManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;

@Route("book-form")
@RequiredArgsConstructor
public class BookFormPage extends BookTemplatePage {
    private final BookFormManager bookManager;
    private BookForm bookForm;

    @PostConstruct
    private void init() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        bookForm = new BookForm();
        bookForm.init();
        bookForm.addListener(BookForm.SaveEvent.class, e -> saveBook(e.getBookInput()));
        bookForm.addListener(BookForm.CancelEvent.class, e -> UI.getCurrent().navigate("list"));

        add(bookForm);
    }

    private void saveBook(BookInput bookInput) {
        try {
            bookManager.persistForm(bookInput);
        } catch (AuthorNameValidationException e) {
            bookForm.setInvalidAuthorFields();
            showNotification(e.getMessage());
        } catch (BookApplicationException e) {
            showNotification("Book cannot be saved! Please contact administrator!");
        }
    }
}