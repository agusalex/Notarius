package com.Notarius.view.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DialogConfirmacion extends CustomComponent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Label infoLabel = new Label("", ContentMode.HTML);
    private final Button yesButton = new Button("Si", VaadinIcons.CHECK);
    private final Button noButton = new Button("No", VaadinIcons.CLOSE);
    private final Window window = new Window();

    public DialogConfirmacion(String titulo, VaadinIcons iconTitulo, String popupText, String popupWidth,
                              Button.ClickListener yesListener) {

	infoLabel.setSizeFull();
	if (popupText != null) {
	    infoLabel.setValue(popupText);
	}
	yesButton.addStyleName(ValoTheme.BUTTON_DANGER);
	noButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
	final VerticalLayout popupVLayout = new VerticalLayout();
	popupVLayout.setSpacing(true);
	popupVLayout.setMargin(true);
	HorizontalLayout buttonsHLayout = new HorizontalLayout();
	buttonsHLayout.setSpacing(true);
	buttonsHLayout.addComponent(yesButton);
	buttonsHLayout.addComponent(noButton);
	
	window.setHeightUndefined();
	window.setModal(true);
	window.center();
	window.setResizable(false);
	window.setContent(popupVLayout);
	window.setCaption(titulo);
	window.setIcon(iconTitulo);
	yesButton.addClickListener(e -> {
	    window.close();
	});
	if (yesListener != null) {
	    yesButton.addClickListener(yesListener);
	}
	noButton.focus();
	noButton.addClickListener(e -> {
	    window.close();
	});
	// ui
	popupVLayout.addComponent(infoLabel);
	popupVLayout.addComponent(buttonsHLayout);
	popupVLayout.setComponentAlignment(buttonsHLayout, Alignment.TOP_CENTER);
	UI.getCurrent().addWindow(window);
	window.center();
    }

    public void setInfo(String info) {
	infoLabel.setValue(info);
    }

    public void setWindowWidth(String width) {
	window.setWidth(width);
    }

    public void setYesListener(Button.ClickListener yesListener) {
	if (yesListener != null) {
	    yesButton.addClickListener(yesListener);
	}
    }

    public void close() {
	window.close();
    }
}
