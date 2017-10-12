package com.Notarius.view.operacion;

import com.Notarius.data.dto.Operacion;
import com.Notarius.services.OperacionService;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.dialogs.ConfirmDialog;

import java.time.LocalDate;


public class OperacionForm extends FormLayout {

    private Operacion operacion;
    Button save = new Button("Guardar");
    Button delete = new Button("Eliminar");
    TextField asunto = new TextField("Asunto");
    TextField carpeta = new TextField("Carpeta");
    private ComboBox<Operacion.Tipo> tipo = new ComboBox<>("Tipo",Operacion.Tipo.toList());
    private ComboBox<Operacion.Estado> estado = new ComboBox<>("Estado",Operacion.Estado.toList());
    // private NativeSelect<operacion.Sexo> sexo = new NativeSelect<>("Sexo");

    OperacionService service = new OperacionService();
    private OperacionABMView addressbookView;
    private Binder<Operacion> binderOperacion = new Binder<>(Operacion.class);
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering


    public OperacionForm(OperacionABMView addressbook) {
        // setSizeUndefined();

        addressbookView=addressbook;
        configureComponents();
        binding();
        buildLayout();

    }

    private void configureComponents() {
        /*
         * Highlight primary actions.
         *
         * With Vaadin built-in styles you can highlight the primary save button
         *
         * and give it a keyoard shortcut for a better UX.
         */


        delete.setStyleName(ValoTheme.BUTTON_DANGER);
        save.addClickListener(e -> this.save());
        //test.addClickListener(e -> this.test());
        delete.addClickListener(e -> this.delete());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);


    }


    private void binding(){

        asunto.setRequiredIndicatorVisible(true);
        tipo.setRequiredIndicatorVisible(true);
        carpeta.setRequiredIndicatorVisible(true);

        binderOperacion.forField(carpeta).withNullRepresentation("").
                asRequired("Ingrese Una Carpeta")
                .withConverter(new StringToIntegerConverter(
                        "Debe ingresar un numero de carpeta correcto"))
                .withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
                .bind(Operacion::getCarpeta,Operacion::setCarpeta);

        binderOperacion.forField(asunto).asRequired("Ingrese un Asunto").bind(Operacion::getAsunto,Operacion::setAsunto);
        tipo.setEmptySelectionAllowed(false);
        binderOperacion.forField(tipo).asRequired("Seleccione un tipo de Operacion").bind(Operacion::getTipo,Operacion::setTipo);
        estado.setEmptySelectionAllowed(false);
        binderOperacion.forField(estado).asRequired("Seleccione un estado").bind(Operacion::getEstado,Operacion::setEstado);

        binderOperacion.bindInstanceFields(this); //Binding automatico
       /* binderOperacion.forField(nombre).asRequired("Ingrese un nombre").bind(operacion::getNombre,operacion::setNombre);

        binderOperacion.forField(apellido).asRequired("Ingrese un apellido").bind(operacion::getApellido,operacion::setApellido);



        binderOperacion.forField(telefono).asRequired("Ingrese un teléfono").bind(operacion::getTelefono,operacion::setTelefono);


        binderOperacion.forField(mail).withValidator(new EmailValidator(
                "Introduzca un email valido!" )).bind(operacion::getMail,operacion::setMail);
        */

    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);
        tabSheet=new TabSheet();

     /*   contratos.addClickListener(e ->
                new OperacionFormWindow(new operacion()));*/
        FormLayout principal=new FormLayout(carpeta,tipo,asunto,estado);
        principal.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        tabSheet.addTab(principal,"Principal");


        addComponent(tabSheet);
        //HorizontalLayout actions = new HorizontalLayout(save,test,delete);
        HorizontalLayout actions = new HorizontalLayout(save,delete);
        addComponent(actions);
        this.setSpacing(false);
        actions.setSpacing(true);

      //  addStyleName("v-scrollable");

    }


    public void setOperacion(Operacion Operacion) {
   /*   if(operacion.getInquilino()!=null){
            this.calificacion.setVisible(true);
            calificacion.setSelectedItem(Calificacion.A);
            this.calificacion.setSelectedItem(operacion.getInquilino().getCalificacion());
        }
        else{
            this.calificacion.setVisible(false);
        }*/


        this.operacion = Operacion;
        binderOperacion.readBean(Operacion);

        // Show delete button for only Persons already in the database
        delete.setVisible(Operacion.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        //nombre.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();

        if(Operacion.getId()==null){
            this.estado.setValue(com.Notarius.data.dto.Operacion.Estado.Iniciada);
            this.carpeta.setValue(Integer.toString(service.getUltimaCarpeta()+1));
        }




    }


    public void delete() {



           ConfirmDialog.show(this.getUI(),"Eliminar Operacion","Esta seguro que desea eliminar esta operacion?, " +
                           "Despues no podra usar MAS este numero de carpeta, esta accion no es reversible",
                   "Entiendo y deseo eliminar","No eliminar",new ConfirmDialog.Listener() {
                @Override
                public void onClose(ConfirmDialog confirmDialog) {
                    if (confirmDialog.isConfirmed()) {
                        service.delete(operacion);
                        addressbookView.updateList();
                        setVisible(false);
                        getAddressbookView().setComponentsVisible(true);
                        getAddressbookView().showSuccessNotification("Borrado: "+ operacion.toString());

                    }

                }

            });
       }






    private void save() {

        boolean success=false;
        try {
            binderOperacion.writeBean(operacion);
            operacion.setFechaDeIngreso(LocalDate.now());
            service.save(operacion);
            success=true;


        } catch (ValidationException e) {
            e.printStackTrace();
            Notification.show("Error al guardar, por favor revise los campos e intente de nuevo");
            // Notification.show("Error: "+e.getCause());
            return;
        }
        catch (Exception e){
            e.printStackTrace();
            Notification.show("Error: "+e.toString());
        }

        addressbookView.updateList();
       /* String msg = String.format("Guardado '%s %s'.", operacion.getNombre(),
                operacion.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado: "
                    + operacion.toString());


    }

    public void cancel() {
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }



    public OperacionABMView getAddressbookView() {
        return addressbookView;
    }

}