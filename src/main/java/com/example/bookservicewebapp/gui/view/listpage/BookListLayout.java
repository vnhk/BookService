package com.example.bookservicewebapp.gui.view.listpage;

import com.example.bookservicewebapp.model.Book;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

import java.util.List;

public class BookListLayout extends VerticalLayout {
    private final static String LABEL_VALUE = "Books";
    private final static String NEW_BOOK_BUTTON_VALUE = "Add book";
    private final static String NO_RECORDS_LABEL_VALUE = "No records";
    private List<Book> books;
    private boolean isNoDataForBooks;
    @Getter
    private Label titleLabel;
    @Getter
    private Grid<Book> bookGrid;
    @Getter
    private Button newBookButton;

    public void initGUI(List<Book> books) {
        titleLabel = new Label();
        bookGrid = new Grid<>(Book.class);
        newBookButton = new Button(NEW_BOOK_BUTTON_VALUE);

        this.books = books;
        this.isNoDataForBooks = books.size() == 0;
        initLabel();
        initGrid();
        initButton();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void initLabel() {
        if (isNoDataForBooks) {
            titleLabel.setText(NO_RECORDS_LABEL_VALUE);
        } else {
            titleLabel.setText(LABEL_VALUE);
        }
    }

    private void initGrid() {
        if (isNoDataForBooks) {
            bookGrid.setHeight("100px");
        } else {
            bookGrid.setItems(books);
        }
    }

    private void initButton() {
        newBookButton.addClickListener(click -> {
            UI.getCurrent().navigate("book-form");
        });
    }

    public void show() {
        add(titleLabel);
        add(bookGrid);
        add(newBookButton);
    }
}
