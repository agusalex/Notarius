package com.Notarius.view.movimiento;

import com.Notarius.data.dto.ClaseMovimiento;
import com.Notarius.data.dto.Movimiento;
import com.Notarius.data.dto.TipoMoneda;
import com.Notarius.data.dto.TipoMovimiento;
import com.Notarius.services.MovimientoService;
import com.Notarius.view.component.DeleteButton;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    private Movimiento movimiento;
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
        binderMovimiento.forField(monto).withNullRepresentation("0").asRequired("Ingrese un Monto")
                .withConverter(new StringToBigDecimalConverter("Debe ingresar un numero de monto correcto"))
                .bind(Movimiento::getMonto,Movimiento::setMonto);
        binderMovimiento.forField(descripcion).asRequired("Ingrese un Asunto").bind(Movimiento::getDescripcionMovimiento,Movimiento::setDescripcionMovimiento);
        binderMovimiento.forField(tipo).asRequired("Seleccione un tipo de movimiento").bind(Movimiento::getTipoMovimiento,Movimiento::setTipoMovimiento);
        clase.setEmptySelectionAllowed(false);
        binderMovimiento.forField(clase).asRequired("Seleccione un clase").bind(Movimiento::getClaseMovimiento,Movimiento::setClaseMovimiento);
        tipo.setEmptySelectionAllowed(false);
        binderMovimiento.forField(fecha).bind(movimiento -> {
                    Date fecha=movimiento.getFecha();
                    if(fecha!=null)
                        return fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    else{
                        return LocalDate.now();
                    }
                },
                (persona,fecha)->{
                    persona.setFecha(
                            Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant())
                    );
                });
        binderMovimiento.forField(moneda).asRequired("Seleccione una moneda").bind(Movimiento::getTipoMoneda,Movimiento::setTipoMoneda);
        moneda.setEmptySelectionAllowed(false);

    /*    binderMovimiento.forField(monto).withNullRepresentation("").
                asRequired("Ingrese Una Carpeta")
                .withConverter(new StringToIntegerConverter(
                        "Debe ingresar un numero de monto correcto"))
                .withValidator(n -> n >= 0, "Debe ingresar un número no negativo")
                .bind(movimiento::getCarpeta,movimiento::setCarpeta);

        binderMovimiento.forField(descripcion).asRequired("Ingrese un Asunto").bind(movimiento::getAsunto,movimiento::setAsunto);
        tipo.setEmptySelectionAllowed(false);
        binderMovimiento.forField(tipo).asRequired("Seleccione un tipo de movimiento").bind(movimiento::getTipo,movimiento::setTipo);
        clase.setEmptySelectionAllowed(false);
        binderMovimiento.forField(clase).asRequired("Seleccione un clase").bind(movimiento::getEstado,movimiento::setEstado);

        binderMovimiento.bindInstanceFields(this); //Binding automatico*/
       /* binderMovimiento.forField(nombre).asRequired("Ingrese un nombre").bind(movimiento::getNombre,movimiento::setNombre);

        binderMovimiento.forField(apellido).asRequired("Ingrese un apellido").bind(movimiento::getApellido,movimiento::setApellido);



        binderMovimiento.forField(telefono).asRequired("Ingrese un teléfono").bind(movimiento::getTelefono,movimiento::setTelefono);


        binderMovimiento.forField(mail).withValidator(new EmailValidator(
                "Introduzca un email valido!" )).bind(movimiento::getMail,movimiento::setMail);
        */

    }

    private void buildLayout() {
        setSizeFull();
        setMargin(true);
        tabSheet=new TabSheet();

     /*   contratos.addClickListener(e ->
                new MovimientoFormWindow(new movimiento()));*/
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
   /*   if(movimiento.getInquilino()!=null){
            this.calificacion.setVisible(true);
            calificacion.setSelectedItem(Calificacion.A);
            this.calificacion.setSelectedItem(movimiento.getInquilino().getCalificacion());
        }
        else{
            this.calificacion.setVisible(false);
        }*/


        this.movimiento = Movimiento;
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


        service.delete(movimiento);
        addressbookView.updateList();
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);
        getAddressbookView().showSuccessNotification("Borrado: " + movimiento.toString());



    }







    private void save() {

        boolean success=false;
        try {
            binderMovimiento.writeBean(movimiento);
            service.save(movimiento);
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
       /* String msg = String.format("Guardado '%s %s'.", movimiento.getNombre(),
                movimiento.getApellido());*
        Notification.show(msg, Type.TRAY_NOTIFICATION);*/
        setVisible(false);
        getAddressbookView().setComponentsVisible(true);


        if(success)
            getAddressbookView().showSuccessNotification("Guardado: "
                    + movimiento.toString());


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