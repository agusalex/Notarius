package com.Notarius.view.uploader;




import com.Notarius.data.dto.Operacion;
import com.Notarius.services.DashboardEvent;
import com.Notarius.services.OperacionService;
import com.Notarius.view.component.DefaultLayout;
import com.Notarius.view.component.ImageUploader;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Uploader")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class UploaderView extends DefaultLayout implements View {

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    TextField filter = new TextField();
    private Grid<Operacion> grid = new Grid<>(Operacion.class);
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    RadioButtonGroup<String> filtroRoles= new RadioButtonGroup<>();





    HorizontalLayout mainLayout;
    // OperacionForm is an example of a custom component class
    private boolean isonMobile=false;

    // OperacionService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    OperacionService service = new OperacionService();


    public UploaderView(){

        super();
        buildLayout();
        configureComponents();
    }





    private void configureComponents() {
        /*
         * Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends only
         * the needed changes to the web page without loading a new page.
         */
        //    newItem.addClickListener(e -> operacionForm.setOperacion(new Operacion()));

        filter.addValueChangeListener(e -> updateList());
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setPlaceholder("Filtrar");
        filter.setIcon(VaadinIcons.SEARCH);
        filter.addValueChangeListener(e -> updateList());
        clearFilterTextBtn.setDescription("Limpiar filtro");
        clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());
        grid.setColumns("carpeta", "asunto");
        grid.getColumns().get(0).setStyleGenerator(person -> "rightalign");
        Responsive.makeResponsive(this);
        grid.asSingleSelect().addValueChangeListener(event -> {
            Operacion operacion=event.getValue();
            if(operacion!=null)
              new ImageUploader(operacion) {
                  @Override
                  public void onClose() {
                      if(operacion.getPathImagenes().size()>0)
                        operacion.setEstado(Operacion.Estado.Inscripta);
                      OperacionService.getService().save(operacion);
                      updateList();
                  }
              };
        });

        if(isonMobile){
            filter.setWidth("100%");
        }
        updateList();
    }
    

    private void buildLayout() {

        CssLayout filtering = new CssLayout();
        HorizontalLayout hl= new HorizontalLayout();
        filtering.addComponents(filter, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        hl.addComponent(filtering);


        buildToolbar("Inscripciones",hl);
        grid.setSizeFull();
        mainLayout = new HorizontalLayout(grid);
        mainLayout.setSizeFull();
        addComponent(mainLayout);
        this.setExpandRatio(mainLayout, 1);

    }

    /*
     * Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that handle the
     * back-end access and/or the user interface updates. You can further split
     * your code into classes to easier maintenance. With Vaadin you can follow
     * MVC, MVP or any other design pattern you choose.
     */

    public void showErrorNotification(String notification){
        Notification success = new Notification(
                notification);
        success.setDelayMsec(4000);
        success.setStyleName("bar error small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

    public void showSuccessNotification(String notification){
        Notification success = new Notification(
                notification);
        success.setDelayMsec(2000);
        success.setStyleName("bar success small");
        success.setPosition(Position.BOTTOM_CENTER);
        success.show(Page.getCurrent());
    }

    public void updateList() {
        List<Operacion> customers = service.findAllIniciadas(filter.getValue());
        grid.setItems(customers);
    }

    public void filter() {
        grid.setItems(service.findAllIniciadas(filter.getValue()));
    }

    public boolean isIsonMobile() {
        return isonMobile;
    }

    public void ClearFilterBtnAction(){
        filter.clear();
    }


    @Override
    public void detach() {
        super.detach();
        // A new instance of TransactionsView is created every time it's
        // navigated to so we'll need to clean up references to it on detach.

    }
    @Subscribe
    public void browserWindowResized(final DashboardEvent.BrowserResizeEvent event) {
        if (Page.getCurrent().getBrowserWindowWidth() < 800) {

            isonMobile=true;
        }
        else{
            isonMobile=false;

        }

    }

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent event) {
    }


}
