package com.Notarius.view.adressbook;

import com.Notarius.data.dto.Persona;
import com.Notarius.services.PersonaService;
import com.Notarius.view.component.BlueLabel;
import com.Notarius.view.component.DeleteButton;
import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import net.sf.cglib.core.Local;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class PersonaForm extends FormLayout {
    private Persona persona;
    private Button save = new Button("Guardar");
    // Button test = new Button("Test");
    private DeleteButton delete = new DeleteButton("Eliminar",
            VaadinIcons.WARNING, "Eliminar", "20%", new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            delete();
        }
    });

    private TextField nombre = new TextField("Nombre");
    private TextField apellido = new TextField("Apellido");
    private TextField DNI = new TextField("DNI");
    private TextField telefono = new TextField("Celular");
    private TextField telefono2 = new TextField("Telefono");
    private TextField mail = new TextField("Mail");
    private TextArea infoAdicional = new TextArea("Info");

    private TextField CUIT = new TextField("CUIT/L");

    private TextField nombreMadre = new TextField("Nombre Madre");

    private TextField nombrePadre = new TextField("Nombre Padre");
    private RadioButtonGroup<Persona.Sex> sexo = new RadioButtonGroup<Persona.Sex>("Sexo");
    private ComboBox<Persona.MaritalStatus> estadoCivil = new ComboBox<>("Estado Civil");
    DateField fechaDeNac = new DateField("Fecha.de Nac");
    TextField pais = new TextField("Pais");
    private PersonaService personaService = new PersonaService();
    private PersonaABMView addressbookView;
    private Binder<Persona> binderPersona = new Binder<>(Persona.class);

    // TabSheet
    private FormLayout principal;
    private FormLayout adicional;
    private TabSheet personaFormTabSheet;

    // Easily binding forms to beans and manage validation and buffering

    public PersonaForm(PersonaABMView addressbook) {
        // setSizeUndefined();

        addressbookView = addressbook;
        configureComponents();
        binding();
        buildLayout();

    }

    private void configureComponents() {


        sexo=new RadioButtonGroup<>("Sexo",Arrays.asList( Persona.Sex.values()));
        sexo.setStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        estadoCivil.setItems(Persona.MaritalStatus.values());
        // sex.setValue(Persona.Sex.Masculino);
        estadoCivil.setValue(Persona.MaritalStatus.SOLTERO);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        setVisible(false);
        fechaDeNac.setDateFormat("dd/MM/yyyy");
        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void binding() {
        // binder.bindInstanceFields(this); //Binding automatico
        nombre.setRequiredIndicatorVisible(true);
        apellido.setRequiredIndicatorVisible(true);
        DNI.setRequiredIndicatorVisible(false);
        binderPersona.forField(nombre).asRequired("Ingrese un nombre").bind(Persona::getFirstName, Persona::setFirstName);

        binderPersona.forField(apellido).asRequired("Ingrese un apellido").bind(Persona::getLastName,
                Persona::setLastName);

        binderPersona.forField(DNI).bind(Persona::getDNI, Persona::setDNI);

        binderPersona.forField(telefono).bind(Persona::getMobilePhone,
                Persona::setMobilePhone);

        binderPersona.forField(telefono2).bind(Persona::getPhone, Persona::setPhone);

        binderPersona.forField(mail).bind(Persona::getEmail, Persona::setEmail);

        binderPersona.forField(CUIT).bind(Persona::getCuitl, Persona::setCuitl);

        binderPersona.forField(sexo).bind(Persona::getSex,Persona::setSex);

        binderPersona.forField(pais).bind(Persona::getCountryofOrigin,Persona::setCountryofOrigin);

        binderPersona.forField(estadoCivil).bind(Persona::getMaritalStatus,Persona::setMaritalStatus);

        binderPersona.forField(nombreMadre).bind(Persona::getMothersName,Persona::setMothersName);

        binderPersona.forField(nombrePadre).bind(Persona::getFathersName,Persona::setFathersName);

        binderPersona.forField(fechaDeNac).bind(persona -> {
          Date fecha=persona.getBirthDate();
            if(fecha!=null)
                return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            else{
                return LocalDate.now();
            }
        },
        (persona,fecha)->{
            persona.setBirthDate(
                    Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant())
            );
        });



        binderPersona.forField(infoAdicional).bind(Persona::getInfo, Persona::setInfo);


    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);

        personaFormTabSheet = new TabSheet();


        BlueLabel info = new BlueLabel("Información Adicional");
        BlueLabel contacto = new BlueLabel("Contacto");



        /*
         * contratos.addClickListener(e -> new PersonaFormWindow(new Persona()));
         */


        principal = new FormLayout(nombre, apellido, DNI,fechaDeNac,sexo,estadoCivil);
        FormLayout otro = new FormLayout(pais,CUIT,nombreMadre,nombrePadre,contacto, mail, telefono,telefono2);
        adicional = new FormLayout(info, infoAdicional);


        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        otro.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        adicional.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);


        personaFormTabSheet.addTab(principal, "Principal");
        personaFormTabSheet.addTab(otro, "Secundario");
        personaFormTabSheet.addTab(adicional, "Adicional");

        addComponent(personaFormTabSheet);
        // HorizontalLayout actions = new HorizontalLayout(save,test,delete);
        HorizontalLayout actions = new HorizontalLayout(save, delete);
        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

        // addStyleName("v-scrollable");

    }

    public void setPersona(Persona persona) {


        this.persona = persona;
        binderPersona.readBean(persona);

        // Show delete button for only Persons already in the database
        delete.setVisible(persona.getId() != null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        nombre.selectAll();
        if (getAddressbookView().isIsonMobile())
            personaFormTabSheet.focus();

        if(persona.getId()==null)
            estadoCivil.setValue(Persona.MaritalStatus.SOLTERO);

        else
            estadoCivil.setValue(persona.getMaritalStatus());


    }

    private void delete() {
        personaService.delete(persona);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: " + persona.getFirstName() + " " +
                persona.getLastName());

    }


    private void save() {

        boolean success = false;
        try {
            binderPersona.writeBean(persona);
            personaService.save(persona);
            success = true;

        } catch (ValidationException e) {
            Notification.show("Error al guardar, revise los campos");

            return;
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error: " + e.toString());
        }

        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);

        if (success)
            getAddressbookView().showSuccessNotification("Guardado: " + persona.getFirstName()+
                     " " +
                    persona.getLastName());

    }

    public void cancel() {
        if(binderPersona.hasChanges()){
            if(persona.getId()!=null)
                getAddressbookView().showErrorNotification("Cambios Descartados!");
        }
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }

    public PersonaABMView getAddressbookView() {
        return addressbookView;
    }


}

