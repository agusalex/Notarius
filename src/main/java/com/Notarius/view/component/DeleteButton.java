package com.Notarius.view.component;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class DeleteButton extends CustomComponent {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private final Label infoLabel = new Label("Eliminar", ContentMode.HTML);
        private final Button yesButton = new Button("Eliminar", VaadinIcons.CHECK);
        private final Button noButton = new Button("Cancelar", VaadinIcons.CLOSE);
        private final Window window = new Window();
        private Button button;
        public DeleteButton(String caption, VaadinIcons icon, String popupText, String popupWidth,
                            Button.ClickListener yesListener) {


            popupText="¿Esta seguro que desea eliminar?";
            VerticalLayout root = new VerticalLayout();
            setCompositionRoot(root);
            root.setSpacing(false);
            root.setMargin(false);           
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
            window.setCaption("Eliminar");
            window.setIcon(VaadinIcons.WARNING);
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
            
            configureDeleteButton(caption, icon);
            root.addComponent(button);

        }
	private void configureDeleteButton(String caption, VaadinIcons icon) {
	    button = new Button(caption, icon);
            button.setSizeFull();
            button.addStyleName(ValoTheme.BUTTON_DANGER);
            button.setIcon(VaadinIcons.TRASH);
            button.addClickListener(e -> {
                if (window.getParent() == null) {
                    UI.getCurrent().addWindow(window);
                    window.center();
                }
            });
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
       @Override
    public void addStyleNames(String... styles) {
      for(String style : styles) {
	  button.addStyleName(style);
      }
    }
    }

