package com.example.bookservicewebapp.gui.view;

import com.example.bookservicewebapp.gui.view.listpage.BookGrid;
import com.example.bookservicewebapp.model.Book;
import com.vaadin.flow.component.grid.Grid;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookGridTest {
    private BookGrid bookGrid;

    @Test
    public void setupGridTest() {
        bookGrid = new BookGrid(Collections.singletonList(new Book()));

        bookGrid.setupGrid();

        List<Grid.Column<Book>> columns = bookGrid.getColumns();
        assertThat(columns).isNotEmpty().hasSize(3);
    }
}
