package com.Notarius.view.adressbook;


import com.Notarius.data.dto.PersonaDTO;
import com.Notarius.services.ContactService;
import com.Notarius.services.DashboardEvent;
import com.Notarius.services.DashboardEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.util.BeanItemContainer;
import com.vaadin.v7.ui.Grid;
import com.vaadin.v7.ui.TextField;

/* User Interface written in Java.
 *
 * Define the user interface shown on the Vaadin generated web page by extending the UI class.
 * By default, a new UI instance is automatically created when the page is loaded. To reuse
 * the same instance, add @PreserveOnRefresh.
 */

@Title("Addressbook")
@Theme("valo")
@Widgetset("com.vaadin.v7.Vaadin7WidgetSet")
public class AddressbookView extends VerticalLayout implements View {

    /*
     * Hundreds of widgets. Vaadin's user interface components are just Java
     * objects that encapsulate and handle cross-browser support and
     * client-server communication. The default Vaadin components are in the
     * com.vaadin.ui package and there are over 500 more in
     * vaadin.com/directory.
     */
    TextField filter = new TextField();
    Grid contactList = new Grid();
    Button newContact = new Button("Nuevo");



    // ContactForm is an example of a custom component class
    ContactForm contactForm = new ContactForm(this);

    // ContactService is a in-memory mock DAO that mimics
    // a real-world datasource. Typically implemented for
    // example as EJB or Spring Data based service.
    ContactService service = ContactService.getService();

    /*
     * The "Main method".
     *
     * This is the entry point method executed to initialize and configure the
     * visible user interface. Executed on every browser reload because a new
     * instance is created for each web page loaded.
     */
    public AddressbookView(){
        super();
        configureComponents();
        buildLayout();
        setMargin(true);
        newContact.setStyleName(ValoTheme.BUTTON_PRIMARY);

        DashboardEventBus.register(this);
        TabSheet tabs = new TabSheet();
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
        newContact.addClickListener(e -> contactForm.edit(new PersonaDTO()));

        filter.setInputPrompt("Filtrar...");
        filter.addTextChangeListener(e -> refreshContacts(e.getText()));

        contactList
                .setContainerDataSource(new BeanItemContainer<>(PersonaDTO.class));

        contactList.removeAllColumns();
        contactList.addColumn("firstName");
        contactList.addColumn("lastName");
        contactList.addColumn("DNI");
        contactList.getColumn("firstName").setHeaderCaption("Nombre");
        contactList.getColumn("lastName").setHeaderCaption("Apellido");
        contactList.getColumn("DNI").setHeaderCaption("DNI");
        contactList.setColumnOrder("firstName", "lastName", "DNI");
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(
                e -> contactForm.edit((PersonaDTO) contactList.getSelectedRow()));
        refreshContacts();
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



    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, newContact);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, contactList);
        left.setSizeFull();
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);
        addComponent(mainLayout);


    }

    /*
     * Choose the design patterns you like.
     *
     * It is good practice to have separate data access methods that handle the
     * back-end access and/or the user interface updates. You can further split
     * your code into classes to easier maintenance. With Vaadin you can follow
     * MVC, MVP or any other design pattern you choose.
     */
    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        contactList.setContainerDataSource(new BeanItemContainer<>(
                PersonaDTO.class, service.findAll(stringFilter)));
        contactForm.setVisible(false);
    }

    /*
     * Deployed as a Servlet or Portlet.
     *
     * You can specify additional servlet parameters like the URI and UI class
     * name and turn on production mode when you have finished developing the
     * application.
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
