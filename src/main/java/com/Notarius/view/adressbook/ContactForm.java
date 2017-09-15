package com.Notarius.view.adressbook;
import com.Notarius.data.dto.PersonaDTO;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.FieldGroup;
import com.vaadin.v7.ui.DateField;
import com.vaadin.v7.ui.TextField;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to bind data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
    public class ContactForm extends FormLayout {

        Button save = new Button("Guardar", this::save);
        Button cancel = new Button("Cancelar", this::cancel);
        Button delete = new Button("Eliminar", this::delete);




         TextField firstName = new TextField("Nombre");
        TextField lastName = new TextField("Apellido");
        TextField dni = new TextField("DNI");
        TextField phone = new TextField("Celular");
        TextField email = new TextField("Mail");
        DateField birthDate = new DateField("F.de Nac");

        AddressbookView addressbookView;

        PersonaDTO contact;

        // Easily bind forms to beans and manage validation and buffering
        BeanFieldGroup<PersonaDTO> formFieldBindings;

    public ContactForm(AddressbookView addressbook) {
        addressbookView=addressbook;
        configureComponents();
        buildLayout();
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         * and give it a keyboard shortcut for a better UX.
         */
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);
        TabSheet tabSheet=new TabSheet();
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        addComponent(actions);
        actions.setSpacing(true);
        VerticalLayout principal=new VerticalLayout( firstName, lastName, dni, birthDate);
        VerticalLayout contacto=new VerticalLayout( email,phone);

         tabSheet.addTab(principal,"Principal");
        tabSheet.addTab(contacto,"Contacto");

         addComponent(tabSheet);

         addComponent(delete);

    }

    /*
     * Use any JVM language.
     *
     * Vaadin supports all languages supported by Java Virtual Machine 1.6+.
     * This allows you to program user interface in Java 8, Scala, Groovy or any
     * other language you choose. The new languages give you very powerful tools
     * for organizing your code as you choose. For example, you can implement
     * the listener methods in your compositions or in separate controller
     * classes and receive to various Vaadin component events, like button
     * clicks. Or keep it simple and compact with Lambda expressions.
     */
    public void save(Button.ClickEvent event) {
        try {
            // Commit the fields from UI to DAO
            formFieldBindings.commit();

            // Save DAO to backend with direct synchronous service API
            getAddressbookView().service.save(contact);

            String msg = String.format("Saved '%s %s'.", contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg, Type.TRAY_NOTIFICATION);
            getAddressbookView().refreshContacts();
        } catch (FieldGroup.CommitException e) {
            // Validation exceptions could be shown here
        }
        getAddressbookView().newContact.setVisible(true);
    }

    public void cancel(Button.ClickEvent event) {
        // Place to call business logic.
        Notification.show("Cancelado", Type.TRAY_NOTIFICATION);
        getAddressbookView().contactList.select(null);
        setVisible(false);
        getAddressbookView().newContact.setVisible(true);
    }

    private void delete(Button.ClickEvent event) {
            if(contact==null||contact.getId()==null){
                String msg = String.format("No es posible eliminar");
                Notification.show(msg, Type.TRAY_NOTIFICATION);
                return;
            }
            getAddressbookView().service.delete(contact);

            String msg = String.format("Eliminado '%s %s'.", contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg, Type.TRAY_NOTIFICATION);
            getAddressbookView().refreshContacts();
        getAddressbookView().newContact.setVisible(true);

    }

    void edit(PersonaDTO contact) {
        this.contact = contact;
        if (contact != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact,
                    this);
            firstName.focus();

        }
        setVisible(contact != null);
        getAddressbookView().newContact.setVisible(contact == null);

    }

    public AddressbookView getAddressbookView() {
        return addressbookView;
    }



}
