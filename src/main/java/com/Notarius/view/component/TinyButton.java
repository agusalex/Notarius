package com.Notarius.view.component;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public class TinyButton extends Button {

    public TinyButton(String caption){
        super(caption);
        addStyleName(ValoTheme.BUTTON_TINY);

    }

    public TinyButton(String caption, VaadinIcons icon){
        super(caption,icon);
        addStyleName(ValoTheme.BUTTON_TINY);



    }

    public TinyButton(VaadinIcons icons) {
        super(icons);
        addStyleName(ValoTheme.BUTTON_TINY);
    }
}
