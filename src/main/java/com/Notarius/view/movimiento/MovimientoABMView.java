package com.Notarius.view.movimiento;



import com.Notarius.data.dto.Movimiento;
import com.Notarius.services.DashboardEvent;
import com.Notarius.services.MovimientoService;
import com.Notarius.view.component.DefaultLayout;
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

@Title("Movimientos")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class MovimientoABMView extends DefaultLayout implements View {

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    TextField filter = new TextField();
    private Grid<Movimiento> grid = new Grid<>(Movimiento.class);
    Button newItem = new Button("Nuevo");
    Button clearFilterTextBtn = new Button(VaadinIcons.CLOSE);
    RadioButtonGroup<String> filtroRoles= new RadioButtonGroup<>();
    Button seleccionFiltro=new Button(VaadinIcons.SEARCH);
    Window sw = new Window("Filtrar");





    HorizontalLayout mainLayout;
    // MovimientoForm is an example of a custom component class
    MovimientoForm movimientoForm = new MovimientoForm(this);
    private boolean isonMobile=false;

    // MovimientoService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    MovimientoService service = new MovimientoService();


    public MovimientoABMView(){

        super();
        buildLayout();
        configureComponents();
       /* UI.getCurrent().getPage().getStyles().add(".v-grid-row.finalizada {color: darkgreen;}" +
                "}");
        UI.getCurrent().getPage().getStyles().add(".v-grid-row.descartada {color: darkred;}");*/


    }





    private void configureComponents() {
        /*
         * Synchronous event handling.
         *
         * Receive user interaction events on the server-side. This allows you
         * to synchronously handle those events. Vaadin automatically sends only
         * the needed changes to the web page without loading a new page.
         */
        //    newItem.addClickListener(e -> movimientoForm.setMovimiento(new Movimiento()));

        filter.addValueChangeListener(e -> updateList());
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.setPlaceholder("Filtrar");
        filter.addValueChangeListener(e -> updateList());
        clearFilterTextBtn.setDescription("Limpiar filtro");
        clearFilterTextBtn.addClickListener(e -> ClearFilterBtnAction());

        newItem.addClickListener(e -> {
            grid.asSingleSelect().clear();
            movimientoForm.setMovimiento(new Movimiento());
        });





        grid.setColumns("tipoMovimiento", "monto", "tipoMoneda","claseMovimiento","descripcionMovimiento");


       /* grid.setStyleGenerator(movimiento -> {
            String ret = null;
            if(movimiento.getEstado()!=null) {
                if (movimiento.getEstado().equals(Movimiento.Estado.Finalizada)) {
                    ret = "finalizada";
                }
                else  if (movimiento.getEstado().equals(Movimiento.Estado.Descartada)) {
                    ret = "descartada";
                }
            }
            return ret;
        });*/



        grid.getColumns().get(0).setStyleGenerator(person -> "rightalign");



        Responsive.makeResponsive(this);
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                if (movimientoForm.isVisible())
                    setComponentsVisible(true);
                movimientoForm.setVisible(false);
            } else {
                movimientoForm.setMovimiento(event.getValue());
            }
        });

        // grid.setSelectionMode(Grid.SelectionMod
        //
        // e.SINGLE);

        if(isonMobile){
            filter.setWidth("100%");
        }
        newItem.setStyleName(ValoTheme.BUTTON_PRIMARY);
        //filter.setIcon(VaadinIcons.SEARCH);
        //filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);


        updateList();
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

    public void setComponentsVisible(boolean b){
        newItem.setVisible(b);
        filter.setVisible(b);
        seleccionFiltro.setVisible(b);
        //clearFilterTextBtn.setVisible(b);
        if(isonMobile)
            grid.setVisible(b);

    }

    private void buildLayout() {

        CssLayout filtering = new CssLayout();
        HorizontalLayout hl= new HorizontalLayout();
        filtering.addComponents(seleccionFiltro,filter, clearFilterTextBtn,newItem);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        hl.addComponent(filtering);


        buildToolbar("Movimientos",hl);
        grid.setSizeFull();
        mainLayout = new HorizontalLayout(grid, movimientoForm);
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
        List<Movimiento> customers = service.findAll(filter.getValue());
        grid.setItems(customers);
    }

    public void filter() {
        grid.setItems(service.findAll(filter.getValue()));
    }

    public boolean isIsonMobile() {
        return isonMobile;
    }

    public void ClearFilterBtnAction(){
        if(this.movimientoForm.isVisible()){
            newItem.focus();
            movimientoForm.cancel();

        }
        filter.clear();
    }



    /*

     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class
     * name and turn on production mode when you have finished developing the
     * application.
     */
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
