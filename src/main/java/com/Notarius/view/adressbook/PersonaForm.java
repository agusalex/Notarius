package com.Notarius.view.adressbook;
import com.Notarius.data.dto.Persona;
import com.Notarius.view.component.DialogConfirmacion;
import com.Notarius.view.misc.UploadReceiver;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.v7.data.fieldgroup.BeanFieldGroup;
import com.vaadin.v7.data.fieldgroup.FieldGroup;
import com.vaadin.v7.ui.DateField;
import com.vaadin.v7.ui.TextField;
import com.vaadin.v7.ui.TextArea;
import com.vaadin.v7.ui.ComboBox;
import org.vaadin.dialogs.ConfirmDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* Create custom UI Components.
 *
 * Create your own Vaadin components by inheritance and composition.
 * This is a form component inherited from VerticalLayout. Use
 * Use BeanFieldGroup to bind data fields from DTO to UI fields.
 * Similarly named field by naming convention or customized
 * with @PropertyId annotation.
 */
    public class PersonaForm extends FormLayout {

        Button save = new Button("Guardar", this::save);
        Button cancel = new Button("Cancelar", this::cancel);
        Button delete = new Button("Eliminar", this::deleteConfirmationDialog);


         TextField firstName = new TextField("Nombre");
        TextField lastName = new TextField("Apellido");
        TextField dni = new TextField("DNI");
        TextField cuitl = new TextField("CUIL/T");
        TextField mothersName = new TextField("Nombre Madre");
        TextField fathersName = new TextField("Nombre Padre");
        TextArea info=new TextArea("Informacion Adicional");


        ComboBox sex;
        ComboBox countryofOrigin;
        ComboBox maritalStatus;






        TextField mobilePhone = new TextField("Celular");
        TextField phone = new TextField("Teléfono");
        TextField email = new TextField("Mail");
        DateField birthDate = new DateField("Fecha.de Nac");
        Upload upload ;


        ABMPersonaView ABMPersonaView;

        Persona contact;

        // Easily bind forms to beans and manage validation and buffering
        BeanFieldGroup<Persona> formFieldBindings;


    public PersonaForm(ABMPersonaView addressbook) {
        ABMPersonaView =addressbook;
        configureComponents();
        setMargin(true);
        buildLayout();
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        addStyleName("v-scrollable");
        setHeight("100%");
    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         * and give it a keyboard shortcut for a better UX.
         */



       String[] locales=Locale.getISOCountries();
       List <String> countries=new ArrayList<>();
       Arrays.stream(locales).forEach(
              country -> countries.add(new Locale("",country).getDisplayCountry()));



       countryofOrigin=new ComboBox("Nacionalidad",countries);
        maritalStatus=new ComboBox("Estado Civil",Arrays.asList( Persona.MaritalStatus.values()));
        sex=new ComboBox("Sexo",Arrays.asList( Persona.Sex.values()));
       countryofOrigin.setValue("Argentina");
       maritalStatus.setValue(Persona.MaritalStatus.SOLTERO);
      // sex.setValue(Persona.Sex.Masculino);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        UploadReceiver upr=new UploadReceiver();
        upload= new Upload("Subir DNI",upr);
        upload.setButtonCaption("Subir");

        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);
        TabSheet tabSheet=new TabSheet();
        HorizontalLayout actions = new HorizontalLayout(save, cancel);
        addComponent(actions);
        actions.setSpacing(true);
        VerticalLayout principal=new VerticalLayout( firstName, lastName, sex, birthDate,dni,cuitl,countryofOrigin,maritalStatus);
        VerticalLayout contacto=new VerticalLayout( mothersName,fathersName,email,mobilePhone,phone);
        VerticalLayout misc= new VerticalLayout(info,upload);

        tabSheet.addTab(principal,"Basico");
        tabSheet.addTab(contacto,"Contacto");
        tabSheet.addTab(misc,"Misc.");



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
            getABMPersonaView().service.save(contact);

            String msg = String.format("Guardado '%s %s'.", contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg, Type.TRAY_NOTIFICATION);
            getABMPersonaView().refreshContacts();
        } catch (FieldGroup.CommitException e) {
            // Validation exceptions could be shown here
        }
        getABMPersonaView().newContact.setVisible(true);
    }

    public void cancel(Button.ClickEvent event) {
        // Place to call business logic.
        Notification.show("Cancelado", Type.TRAY_NOTIFICATION);
        getABMPersonaView().contactList.select(null);
        setVisible(false);
        getABMPersonaView().newContact.setVisible(true);
    }



    public void deleteConfirmationDialog(Button.ClickEvent event){
        DialogConfirmacion dialog = new DialogConfirmacion("Eliminar Operacion",
                VaadinIcons.WARNING,
                "¿Esta seguro que desea eliminar esta operacion?",
                "100px",
                confirmacion -> {
                   delete(event);
                });

    }

    private void delete(Button.ClickEvent event) {
            if(contact==null||contact.getId()==null){
                String msg = String.format("No es posible eliminar");
                Notification.show(msg, Type.TRAY_NOTIFICATION);
                return;
            }
            getABMPersonaView().service.delete(contact);

            String msg = String.format("Eliminado '%s %s'.", contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg, Type.TRAY_NOTIFICATION);
            getABMPersonaView().refreshContacts();
        getABMPersonaView().newContact.setVisible(true);

    }

    void edit(Persona contact) {
        this.contact = contact;
        if (contact != null) {
            // Bind the properties of the contact POJO to fiels in this form
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact,
                    this);
            firstName.focus();
            if(contact.getId()==null){
                delete.setVisible(false);
            }
            else{
                delete.setVisible(true);}

        }
        setVisible(contact != null);
        getABMPersonaView().newContact.setVisible(contact == null);

    }

    public ABMPersonaView getABMPersonaView() {
        return ABMPersonaView;
    }



}
