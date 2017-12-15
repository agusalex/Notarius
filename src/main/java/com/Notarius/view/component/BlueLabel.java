package com.Notarius.view.component;

import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class BlueLabel extends Label {
    public BlueLabel(String caption){
        super(caption);
      addStyleName(ValoTheme.LABEL_H4);
      addStyleName(ValoTheme.LABEL_COLORED);
    }


}
