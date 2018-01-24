package com.Notarius.view.movimiento;

import com.Notarius.data.dto.ClaseMovimiento;
import com.Notarius.data.dto.Movimiento;
import com.Notarius.data.dto.TipoMoneda;
import com.Notarius.data.dto.TipoMovimiento;
import com.Notarius.services.MovimientoService;
import com.Notarius.view.component.DeleteButton;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
/*
	@Column(name = "descripcionMovimiento")
	private String descripcionMovimiento;

	@Column(name = "monto")
	private BigDecimal monto;

	@Column(name = "fecha")
	private LocalDate fecha;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipoMovimiento")
	private TipoMovimiento tipoMovimiento;

	@Enumerated(EnumType.STRING)
	@Column(name =  "claseMovimiento")
	private ClaseMovimiento claseMovimiento;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "tipoMoneda")
	private TipoMoneda tipoMoneda;*/

public class MovimientoForm extends FormLayout {

    private Movimiento Movimiento;
    Button save = new Button("Guardar");
    TextField descripcion = new TextField("Descripcion");
    TextField monto = new TextField("Monto");
    private ComboBox<TipoMoneda> moneda = new ComboBox<>("Moneda",TipoMoneda.toList());
    private ComboBox<TipoMovimiento> tipo = new ComboBox<>("Tipo",TipoMovimiento.toList());
    private ComboBox<ClaseMovimiento> clase = new ComboBox<>("Clase",ClaseMovimiento.toList());
    private DateField fecha = new DateField("Fecha");

    private DeleteButton delete = new DeleteButton("Eliminar",
            VaadinIcons.WARNING, "Eliminar", "20%", new Button.ClickListener() {
        @Override
        public void buttonClick(Button.ClickEvent clickEvent) {
            delete();
        }
    });

    MovimientoService service = new MovimientoService();
    private MovimientoABMView addressbookView;
    private Binder<Movimiento> binderMovimiento = new Binder<>(Movimiento.class);
    TabSheet tabSheet;

    // Easily binding forms to beans and manage validation and buffering


    public MovimientoForm(MovimientoABMView addressbook) {
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
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);


    }


    private void binding(){

        descripcion.setRequiredIndicatorVisible(true);

        monto.setRequiredIndicatorVisible(true);

    /*    binderMovimiento.forField(monto).withNullRepresentation("").
                asRequired("Ingrese Una Carpeta")
                .withConverter(new StringToIntegerConverter(
                        "Debe ingresar un numero de monto correcto"))
                .withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
                .bind(Movimiento::getCarpeta,Movimiento::setCarpeta);

        binderMovimiento.forField(descripcion).asRequired("Ingrese un Asunto").bind(Movimiento::getAsunto,Movimiento::setAsunto);
        tipo.setEmptySelectionAllowed(false);
        binderMovimiento.forField(tipo).asRequired("Seleccione un tipo de Movimiento").bind(Movimiento::getTipo,Movimiento::setTipo);
        clase.setEmptySelectionAllowed(false);
        binderMovimiento.forField(clase).asRequired("Seleccione un clase").bind(Movimiento::getEstado,Movimiento::setEstado);

        binderMovimiento.bindInstanceFields(this); //Binding automatico*/
       /* binderMovimiento.forField(nombre).asRequired("Ingrese un nombre").bind(Movimiento::getNombre,Movimiento::setNombre);

        binderMovimiento.forField(apellido).asRequired("Ingrese un apellido").bind(Movimiento::getApellido,Movimiento::setApellido);



        binderMovimiento.forField(telefono).asRequired("Ingrese un teléfono").bind(Movimiento::getTelefono,Movimiento::setTelefono);


        binderMovimiento.forField(mail).withValidator(new EmailValidator(
                "Introduzca un email valido!" )).bind(Movimiento::getMail,Movimiento::setMail);
        */

    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);
        tabSheet=new TabSheet();

     /*   contratos.addClickListener(e ->
                new MovimientoFormWindow(new Movimiento()));*/
        FormLayout principal=new FormLayout(tipo,monto,moneda,clase ,fecha,descripcion);
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


    public void setMovimiento(Movimiento Movimiento) {
   /*   if(Movimiento.getInquilino()!=null){
            this.calificacion.setVisible(true);
            calificacion.setSelectedItem(Calificacion.A);
            this.calificacion.setSelectedItem(Movimiento.getInquilino().getCalificacion());
        }
        else{
            this.calificacion.setVisible(false);
        }*/


        this.Movimiento = Movimiento;
        binderMovimiento.readBean(Movimiento);

        // Show delete button for only Persons already in the database
        delete.setVisible(Movimiento.getId()!=null);

        setVisible(true);
        getAddressbookView().setComponentsVisible(false);
        //nombre.selectAll();
        if(getAddressbookView().isIsonMobile())
            tabSheet.focus();




    }


    public void delete() {


        service.delete(Movimiento);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: " + Movimiento.toString());



    }







    private void save() {

        boolean success=false;
        try {
            binderMovimiento.writeBean(Movimiento);
            service.save(Movimiento);
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
       /* String msg = String.format("Guardado '%s %s'.", Movimiento.getNombre(),
                Movimiento.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado: "
                    + Movimiento.toString());


    }

    public void cancel() {
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
    }



    public MovimientoABMView getAddressbookView() {
        return addressbookView;
    }

}