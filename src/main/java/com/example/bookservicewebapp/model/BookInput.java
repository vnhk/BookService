package com.example.bookservicewebapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BookInput {
    @NotNull
    @Size(min = 1, max = 100, message = "Author forename must have 1-100 characters length!")
    private String authorForename;
    @NotNull
    @Size(min = 1, max = 100, message = "Author surname must have 1-100 characters length!")
    private String authorSurname;
    @NotNull
    @Size(min = 1, max = 100, message = "Title must have 1-100 characters length!")
    private String title;
    @NotNull
    @Size(min = 13, max = 13, message = "ISBN must have 13 characters length!")
    private String isbn;
}
