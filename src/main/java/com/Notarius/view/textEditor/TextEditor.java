package com.Notarius.view.textEditor;

import com.Notarius.view.component.DefaultLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;

import com.vaadin.ui.Notification;
import org.vaadin.tinymceeditor.TinyMCETextField;

import java.util.Locale;


@Title("Uploader")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class TextEditor  extends DefaultLayout implements View{

    public TextEditor() {

        super();

        TinyMCETextField  tinyMCETextField = new TinyMCETextField();
        addComponent(tinyMCETextField);


    }

    public void showSuccessNotification(String notification){
        Notification success = new Notification(
                notification);
        success.setDelayMsec(2000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

}
