package com.example.bookservicewebapp.gui.view;

import com.example.bookservicewebapp.gui.view.listpage.BookListLayout;
import com.example.bookservicewebapp.model.Book;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookListLayoutTest {
    private BookListLayout bookListLayout;

    @Test
    public void initGUITestNoRecords() {
        bookListLayout = new BookListLayout();
        bookListLayout.initGUI(new ArrayList<>());

        assertEquals("No records", bookListLayout.getTitleLabel().getText());
        assertThat(((ListDataProvider) (bookListLayout.getBookGrid().getDataProvider())).getItems())
                .hasSize(0);
    }

    @Test
    public void initGUITestWithRecords() {
        bookListLayout = new BookListLayout();

        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());
        books.add(new Book());

        bookListLayout.initGUI(books);

        assertEquals("Books", bookListLayout.getTitleLabel().getText());
        assertThat(((ListDataProvider) (bookListLayout.getBookGrid().getDataProvider())).getItems())
                .hasSize(books.size());
    }

    @Test
    public void showTest() {
        bookListLayout = new BookListLayout();
        int componentCount = bookListLayout.getComponentCount();
        
        bookListLayout.initGUI(new ArrayList<>());
        bookListLayout.show();

        assertEquals(componentCount + 3, bookListLayout.getComponentCount());
    }
}
