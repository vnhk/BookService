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
    protected Notification notification;
    private Button notificationCloseButton;

    @PostConstruct
    private void init() {
        initNotification();
    }

    private void initNotification() {
        notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notificationCloseButton = new Button(new Icon("lumo", "cross"));
        notificationCloseButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        notificationCloseButton.getElement().setAttribute("aria-label", "Close");
        notificationCloseButton.addClickListener(event -> {
            notification.close();
        });
    }

    protected void showNotification(String message) {
        notification.removeAll();

        Div text = new Div(new Text(message));
        HorizontalLayout layout = new HorizontalLayout(text, notificationCloseButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
}
