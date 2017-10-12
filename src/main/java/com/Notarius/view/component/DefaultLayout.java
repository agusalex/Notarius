package com.Notarius.view.component;

import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public abstract class DefaultLayout extends VerticalLayout {

    HorizontalLayout tools;
    HorizontalLayout header;


    public DefaultLayout(){
        super();
        setSizeFull();
        addStyleName("transactions");
        addStyleName("v-scrollable");
        setMargin(false);
        setSpacing(false);
        Responsive.makeResponsive(this);
    }



    public void buildToolbar(String Title, Component... Components ){

        header = new HorizontalLayout();
        header.addStyleName("viewheader");
        Responsive.makeResponsive(header);

        Label title = new Label(Title);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        tools =new HorizontalLayout();

        for(Component comp: Components){
            tools.addComponent(comp);

        }
        tools.setSpacing(true);
        Responsive.makeResponsive(tools);

       tools.addStyleName("toolbar");


       if(Page.getCurrent().getBrowserWindowWidth() < 800) {
           tools.setSpacing(false);
           this.addComponent(header);
           this.addComponent(tools);

       }

       else {
           header.addComponent(tools);
           this.addComponent(header);
       }


    }



}



