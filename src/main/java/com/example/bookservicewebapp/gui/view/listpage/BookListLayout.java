package com.example.bookservicewebapp.gui.view.listpage;

import com.example.bookservicewebapp.gui.view.GUILabels;
import com.example.bookservicewebapp.gui.view.RoutePath;
import com.example.bookservicewebapp.model.Book;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.Getter;

import java.util.List;

public class BookListLayout extends VerticalLayout {
    private boolean isNoDataForBooks;
    @Getter
    private Label titleLabel;
    @Getter
    private BookGrid bookGrid;
    private Button newBookButton;
    private Dialog dialog;
    private Button okDialogButton;

    public void initGUI(List<Book> books) {
        titleLabel = new Label();
        newBookButton = new Button(GUILabels.ADD_BOOK_BUTTON_VALUE);

        this.isNoDataForBooks = books.size() == 0;
        initLabel();
        initGrid(books);
        initButton();
        initDialog();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void initDialog() {
        dialog = new Dialog();
        okDialogButton = new Button(GUILabels.OK_BUTTON, e -> dialog.close());
    }

    private VerticalLayout buildDialogLayout(Book book) {
        VerticalLayout verticalLayout = new VerticalLayout();
        Label title = new Label(GUILabels.TITLE + ": " + book.getTitle());
        Label forename = new Label(GUILabels.FORENAME + ": " + book.getAuthor().getForename());
        Label surname = new Label(GUILabels.SURNAME + ": " + book.getAuthor().getSurname());
        Label isbn = new Label(GUILabels.ISBN + ": " + book.getIsbn());

        verticalLayout.add(title, new Hr(), forename, new Hr(), surname, new Hr(), isbn, okDialogButton);
        return verticalLayout;
    }

    private void initLabel() {
        if (isNoDataForBooks) {
            titleLabel.setText(GUILabels.NO_RECORDS_LABEL_VALUE);
        } else {
            titleLabel.setText(GUILabels.LABEL_VALUE);
        }
    }

    private void initGrid(List<Book> books) {
        bookGrid = new BookGrid(books);
        bookGrid.setupGrid();

        bookGrid.addItemClickListener(e -> {
            Book item = e.getItem();
            dialog.removeAll();
            dialog.add(buildDialogLayout(item));
            dialog.open();
        });
    }

    private void initButton() {
        newBookButton.addClickListener(click -> {
            UI.getCurrent().navigate(RoutePath.BOOK_FORM);
        });
    }

    public void show() {
        add(titleLabel);
        add(bookGrid);
        add(newBookButton);
    }
}
