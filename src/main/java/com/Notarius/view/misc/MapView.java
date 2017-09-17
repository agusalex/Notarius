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
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Title("Map")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class MapView extends VerticalLayout implements View {


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
    public MapView() {
        super();
        GoogleMap gmaps=new GoogleMap("AIzaSyBRjRxZJfvG9TfNUx7_r6GmjE--Nfbatd8",null,null);
        gmaps.setSizeFull();
        LatLon latLon=new LatLon(-34.521762, -58.700842);
        GoogleMapMarker marker=new GoogleMapMarker("UNGS",latLon,false);
        gmaps.setCenter(latLon);
        gmaps.setZoom(19);
        gmaps.addMarker(marker);

        addComponent(gmaps);
        setMargin(true);
        DashboardEventBus.register(this);
        setSizeFull();
        addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);


    }





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