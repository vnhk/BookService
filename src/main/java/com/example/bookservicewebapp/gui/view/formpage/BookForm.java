package com.example.bookservicewebapp.gui.view.formpage;

import com.example.bookservicewebapp.model.BookInput;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

public class BookForm extends FormLayout {
    private static final String NEW_BOOK_BUTTON_VALUE = "Save";
    @Getter
    private Label titleLabel;
    @Getter
    private TextField authorForename;
    @Getter
    private TextField authorSurname;
    @Getter
    private TextField title;
    @Getter
    private TextField isbn;
    private Binder<BookInput> binder;
    private Button save;
    private Button cancel;

    public void init() {
        titleLabel = new Label();

        authorForename = new TextField("Forename");
        authorForename.setRequired(true);
        authorForename.setClearButtonVisible(true);

        authorSurname = new TextField("Surname");
        authorSurname.setRequired(true);
        authorSurname.setClearButtonVisible(true);

        title = new TextField("Title");
        title.setRequired(true);
        title.setClearButtonVisible(true);

        isbn = new TextField("ISBN");
        isbn.setRequired(true);
        isbn.setClearButtonVisible(true);

        initValidation();
        addToLayout();
    }

    public void setInvalidAuthorFields() {
        authorForename.setInvalid(true);
        authorForename.setErrorMessage("");
        authorSurname.setInvalid(true);
        authorSurname.setErrorMessage("");
    }

    public void initValidation() {
        binder = new BeanValidationBinder<>(BookInput.class);
        binder.bindInstanceFields(this);
    }

    private void addToLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(titleLabel, authorForename, authorSurname, title, isbn, createButtonsLayout());
        add(verticalLayout);
    }

    private HorizontalLayout createButtonsLayout() {
        save = new Button(NEW_BOOK_BUTTON_VALUE);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setEnabled(false);

        cancel = new Button("Cancel");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        addButtonsListeners();

        binder.addStatusChangeListener(statusChangeEvent -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, cancel);
    }

    private void addButtonsListeners() {
        save.addClickListener(buttonClickEvent -> {
            if (binder.isValid()) {
                fireEvent(new SaveEvent(this, getBookInput()));
            }
        });

        cancel.addClickListener(buttonClickEvent -> {
            fireEvent(new CancelEvent(this));
        });
    }

    private BookInput getBookInput() {
        BookInput bookInput = new BookInput();
        bookInput.setTitle(title.getValue());
        bookInput.setAuthorSurname(authorSurname.getValue());
        bookInput.setAuthorForename(authorForename.getValue());
        bookInput.setIsbn(isbn.getValue());

        return bookInput;
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public static abstract class BookFormEvent extends ComponentEvent<BookForm> {
        @Getter
        private final BookInput bookInput;

        protected BookFormEvent(BookForm source, BookInput bookInput) {
            super(source, false);
            this.bookInput = bookInput;
        }
    }

    public static class SaveEvent extends BookFormEvent {
        SaveEvent(BookForm source, BookInput bookInput) {
            super(source, bookInput);
        }
    }

    public static class CancelEvent extends BookFormEvent {
        CancelEvent(BookForm source) {
            super(source, null);
        }
    }
}
