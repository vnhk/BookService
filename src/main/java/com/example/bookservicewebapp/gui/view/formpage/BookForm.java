package com.example.bookservicewebapp.gui.view.formpage;

import com.example.bookservicewebapp.gui.view.GUILabels;
import com.example.bookservicewebapp.model.BookInput;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

@Getter
public class BookForm extends VerticalLayout {
    private Label titleLabel;
    private TextField authorForename;
    private TextField authorSurname;
    private TextField title;
    private TextField isbn;
    private Button save;
    private Button cancel;
    private Binder<BookInput> binder;

    public void init() {
        titleLabel = new Label();

        authorForename = buildTextFieldForm(GUILabels.FORENAME);
        authorSurname = buildTextFieldForm(GUILabels.SURNAME);
        title = buildTextFieldForm(GUILabels.TITLE);
        isbn = buildTextFieldForm(GUILabels.ISBN);

        initValidation();
        addToLayout();

        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
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
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(verticalLayout);
    }

    private TextField buildTextFieldForm(String labelText) {
        TextField textField = new TextField(labelText);
        textField.setRequired(true);
        textField.setClearButtonVisible(true);

        return textField;
    }

    private HorizontalLayout createButtonsLayout() {
        save = new Button(GUILabels.SAVE_BOOK_BUTTON_VALUE);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.setEnabled(false);

        cancel = new Button(GUILabels.CANCEL_BUTTON);
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

        cancel.addClickListener(buttonClickEvent -> fireEvent(new CancelEvent(this)));
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
