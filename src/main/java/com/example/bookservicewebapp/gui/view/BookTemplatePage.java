package com.example.bookservicewebapp.gui.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import javax.annotation.PostConstruct;

@CssImport("./styles/main.css")
public abstract class BookTemplatePage extends VerticalLayout {
    protected Notification errorNotification;
    protected Notification successNotification;
    private Button errorNotificationCloseButton;
    private Button successNotificationCloseButton;

    @PostConstruct
    private void init() {
        initNotifications();
    }

    private void initNotifications() {
        errorNotification = new Notification();
        errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorNotificationCloseButton = initNotificationCloseButton(errorNotification);

        successNotification = new Notification();
        successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        successNotificationCloseButton = initNotificationCloseButton(successNotification);
    }

    protected void showErrorNotification(String message) {
        showNotification(errorNotification, errorNotificationCloseButton, message);
    }

    protected void showSuccessNotification(String message) {
        showNotification(successNotification, successNotificationCloseButton, message);
    }

    private void showNotification(Notification notification, Button notificationCloseButton, String message) {
        notification.removeAll();

        Div text = new Div(new Text(message));
        HorizontalLayout layout = new HorizontalLayout(text, notificationCloseButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    private Button initNotificationCloseButton(Notification notification) {
        Button notificationCloseButton = new Button(new Icon("lumo", "cross"));
        notificationCloseButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        notificationCloseButton.getElement().setAttribute("aria-label", "Close");
        notificationCloseButton.addClickListener(event -> notification.close());

        return notificationCloseButton;
    }
}
