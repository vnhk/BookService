package com.example.bookservicewebapp.gui.view.listpage;

import com.example.bookservicewebapp.gui.view.GUILabels;
import com.example.bookservicewebapp.model.Book;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.renderer.ComponentRenderer;

import java.util.List;

public class BookGrid extends Grid<Book> {
    private final List<Book> books;

    public BookGrid(List<Book> books) {
        super(Book.class);
        this.books = books;
    }

    public void setupGrid() {
        setItems(books);
        addColumns();
    }

    private void addColumns() {
        removeAllColumns();
        addColumn(new ComponentRenderer<>(author -> {
            String forename = author.getAuthor().getForename();
            String surname = author.getAuthor().getSurname();
            return new Text(forename + " " + surname);
        })).setHeader(GUILabels.AUTHOR);

        addColumn(Book::getTitle).setHeader(GUILabels.TITLE);
        addColumn(Book::getIsbn).setHeader(GUILabels.ISBN);
    }
}
