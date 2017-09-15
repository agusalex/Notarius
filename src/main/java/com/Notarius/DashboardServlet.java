package com.Notarius;

import com.vaadin.server.VaadinServlet;

import javax.servlet.ServletException;

@SuppressWarnings("serial")
public class DashboardServlet extends VaadinServlet {

    @Override
    protected final void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new DashboardSessionInitListener());
    }
}