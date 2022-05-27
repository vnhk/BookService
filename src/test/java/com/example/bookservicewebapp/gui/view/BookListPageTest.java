package com.example.bookservicewebapp.gui.view;

import com.example.bookservicewebapp.gui.view.listpage.BookListPage;
import com.example.bookservicewebapp.service.BookManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookListPageTest {
    @Mock
    private BookManager bookManager;

    @Test
    public void showTest() {
        BookListPage bookListPage = new BookListPage(bookManager);
        when(bookManager.getAllBooks()).thenReturn(new ArrayList<>());

        int componentCount = bookListPage.getComponentCount();
        ReflectionTestUtils.invokeMethod(bookListPage, "initGUI");

        assertEquals(componentCount + 1, bookListPage.getComponentCount());
    }
}
