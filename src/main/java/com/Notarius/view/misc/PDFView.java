package com.Notarius.view.misc;


import com.Notarius.services.DashboardEvent;
import com.Notarius.services.DashboardEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.io.InputStream;

@Title("PDF")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class PDFView extends VerticalLayout implements View {


/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

        /*
         * Hundreds of widgets. Vaadin's user interface components are just Java
         * objects that encapsulate and handle cross-browser support and
         * client-server communication. The default Vaadin components are in the
         * com.vaadin.ui package and there are over 500 more in
         * vaadin.com/directory.

         * This is the entry point method executed to initialize and configure the
         * visible user interface. Executed on every browser reload because a new
         * instance is created for each web page loaded.
         */
        public PDFView(){
            super();
            configureComponents();

            setMargin(true);
            DashboardEventBus.register(this);
            setSizeFull();
            addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);


        }





        private void configureComponents() {
        /*
         * Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends only
         * the needed changes to the web page without loading a new page.
         */
            Window window = new Window();
          // window.getContent().setSizeFull();

            Embedded pdf = new Embedded("", new StreamResource(new StreamResource.StreamSource() {
                public InputStream getStream() {
                    InputStream is = PDFView.class.getClassLoader().getResourceAsStream("demo.pdf");
                    return is;
                }
            }, "demo.pdf"));

            pdf.setType(Embedded.TYPE_BROWSER);
            pdf.setMimeType("application/pdf");
            pdf.setSizeFull();

           this.addComponent(pdf);
        }

    /*
     * Robust layouts.
     *
     * Layouts are components that contain other components. HorizontalLayout
     * contains TextField and Button. It is wrapped with a Grid into
     * VerticalLayout for the left side of the screen. Allow user to resize the
     * components with a SplitPanel.
     *
     * In addition to programmatically building layout in Java, you may also
     * choose to setup layout declaratively with Vaadin Designer, CSS and HTML.
     */






        @Subscribe
        public void browserWindowResized(final DashboardEvent.BrowserResizeEvent event) {
            if (Page.getCurrent().getBrowserWindowWidth() < 800) {
                //calendar.setEndDate(calendar.getStartDate());
            }
        }

        @Override
        public void enter(final ViewChangeListener.ViewChangeEvent event) {
        }

    }


