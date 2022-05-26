package com.example.bookservicewebapp.gui.view.listpage;

import com.example.bookservicewebapp.gui.view.BookTemplatePage;
import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.service.BookManager;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.List;

@Route("list")
@RequiredArgsConstructor
@CssImport("./styles/main.css")
public class BookListPage extends BookTemplatePage {
    private final BookManager bookManager;
    private BookListLayout bookListLayout;

    @PostConstruct
    private void initGUI() {
        List<Book> books = bookManager.getAllBooks();

        bookListLayout = new BookListLayout();
        bookListLayout.initGUI(books);
        bookListLayout.show();
        add(bookListLayout);
    }
}
