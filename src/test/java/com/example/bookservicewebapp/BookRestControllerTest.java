package com.example.bookservicewebapp;

import com.example.bookservicewebapp.model.Book;
import com.example.bookservicewebapp.service.AuthorManager;
import com.example.bookservicewebapp.service.BookFormManager;
import com.example.bookservicewebapp.service.BookInputValidator;
import com.example.bookservicewebapp.service.BookManager;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {BookServiceWebApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestControllerTest {

    private DefaultErrorAttributes defaultErrorAttributes;

    @Mock
    private BookManager bookManager;

    @Mock
    private AuthorManager authorManager;

    private BookInputValidator bookInputValidator;

    private BookFormManager bookFormManager;

    private BookRestController bookRestController;

    private RestExceptionHandler exceptionHandler;

    @BeforeEach
    public void initialiseRestAssuredMockMvcStandalone() {
        defaultErrorAttributes = new DefaultErrorAttributes();
        bookInputValidator = new BookInputValidator();
        bookFormManager = new BookFormManager(bookInputValidator, authorManager, bookManager);
        bookRestController = new BookRestController(bookFormManager, bookManager);
        exceptionHandler = new RestExceptionHandler(defaultErrorAttributes);
        RestAssuredMockMvc.standaloneSetup(bookRestController, exceptionHandler);
    }

    @Test
    public void getAllBooksTestWhenNotEmptyResult() {
        when(bookManager.getAllBooks()).thenReturn(List.of(new Book(), new Book()));

        given()
                .when()
                .get("/api/books")
                .then()
                .statusCode(200)
                .body(is(not(emptyArray())));
    }

    @Test
    public void getAllBooksTestWhenEmptyResult() {
        when(bookManager.getAllBooks()).thenReturn(new ArrayList<>());

        given()
                .when()
                .get("/api/books")
                .then()
                .statusCode(200)
                .body(is(equalTo("[]")));
    }

    @Test
    public void addBookTestWhenEmptyRequestBody() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/books")
                .then()
                .statusCode(400);
    }

    @Test
    public void addBookTestWhenAuthorNameValidationFailed() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" +
                        "  \"authorForename\": \"Forename\",\n" +
                        "  \"authorSurname\": \"Surname\",\n" +
                        "  \"title\": \"Title\",\n" +
                        "  \"isbn\": \"12345-789-123\"\n" +
                        "}")
                .when()
                .post("/api/books")
                .then()
                .statusCode(400);
    }
}
