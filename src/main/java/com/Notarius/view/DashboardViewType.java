package com.Notarius.view;

import com.Notarius.view.adressbook.PersonaABMView;
import com.Notarius.view.dashboard.DashboardView;

import com.Notarius.view.operacion.OperacionABMView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    INICIO("Inicio", DashboardView.class, FontAwesome.HOME, true),PERSONAS(
            "Personas", PersonaABMView.class, FontAwesome.USERS, false),OPERACIONES(
            "Operaciones", OperacionABMView.class, VaadinIcons.HANDSHAKE, false) ;

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;

    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
