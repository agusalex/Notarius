package com.Notarius.view.component;

import com.Notarius.data.dto.Operacion;
import com.Notarius.services.OperacionService;
import com.vaadin.event.MouseEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.Set;

public abstract class ImageUploader extends Window {

    public static final String ID = "profilepreferenceswindow";

    DeleteButton delete = new DeleteButton("",
	    VaadinIcons.WARNING, "Eliminar", "20%", e -> delete());
    Button done=new Button("Ok");
    ListSelect<String> select = new ListSelect<>(null);
    private Image preview = new Image(null);
    private Component profiletab;
    private Panel panel;
    private Component footer;
    private VerticalLayout root;
    UploadReceiver uploadReciever = new UploadReceiver();
    UploadButton upload = new UploadButton(uploadReciever);
    Operacion operacion ;
    OperacionService oS= new OperacionService();
	DownloadButton downloadButton=new DownloadButton();

    private void resize() {
	center();
	fireResize();
	addStyleName("v-scrollable");

    }

    private void refreshListSelect() {
	select.setItems(operacion.getPathImagenes());
	if (operacion.getPathImagenes().iterator().hasNext()) {
	    String primera = operacion.getPathImagenes().iterator().next();
	    preview.setSource(OperacionService.GenerarStreamResource(
		    primera));

	} else {
	    System.out.println("SIn imagenes");
	    preview.setSource(new ThemeResource("sinPortada.png"));
	}
	resize();

    }



    private void delete() {
	for (String path : select.getSelectedItems()) {
	    operacion.removePathImagen(path);
	}

	refreshListSelect();
    }

    public ImageUploader(Operacion operacion) {
	this.operacion = operacion;
	refreshListSelect();
	configureComponents();
	addStyleName("v-scrollable");
	select.setItems(operacion.getPathImagenes());
	select.setRows(4);
	select.setResponsive(true);
	select.setSizeFull();

	select.addValueChangeListener(event -> {
	    Set<String> selected = event.getValue();
	    if (selected.size() == 1) {
		if (selected.iterator().hasNext()) {
		    String first = selected.iterator().next();
		    Resource res = OperacionService.GenerarStreamResource(first);
			System.out.println(first);
		    downloadButton.setArchivoFromPath("Files"+File.separator,first);
		    preview.setSource(res);

		}
		resize();
	    }

	});
	if (operacion.getPathImagenes().iterator().hasNext()) {
	    String first = operacion.getPathImagenes().iterator().next();
	    Resource res = OperacionService.GenerarStreamResource(first);
	    preview.setSource(res);

	}

    }

    public abstract void onClose();

    private void configureComponents() {

		upload.addStartedListener(new Upload.StartedListener() {
			@Override
			public void uploadStarted(Upload.StartedEvent event) {
				// TODO Auto-generated method stub

				if(!(event.getFilename().contains(".jpg")||
						event.getFilename().contains(".png")||
						event.getFilename().contains(".gif")||
						event.getFilename().contains(".jpeg")||
						event.getFilename().contains(".bmp")||
						event.getFilename().contains(".raw")||
						event.getFilename().contains(".tif"))){

					Notification.show("Error", "Tipo de archivo invalido", Notification.Type.ERROR_MESSAGE);
					upload.interruptUpload();
				}
			}
	});
	upload.addSucceededListener(success -> {
	    if (uploadReciever.getFileName() != null && uploadReciever.getFileName() != "") {
		operacion.addPathImagen(uploadReciever.getFileName() + uploadReciever.getFileExtension());
		refreshListSelect();
	    }
	});

	preview = new Image(null, null);
	preview.setSizeUndefined();
	// preview.setWidth(100.0f, Unit.PIXELS);
	preview.setCaption(null);

	// delete.setWidth(28.0f,Unit.PERCENTAGE);
	// makePortada.setWidth(28.0f,Unit.PERCENTAGE);
	// upload.setWidth(28.0f,Unit.PERCENTAGE);

	// select.setWidth(40.0f,Unit.PERCENTAGE);
	// addStyleName("profile-window");
	setId(ID);
	Responsive.makeResponsive(this);

	setModal(true);
	setCloseShortcut(ShortcutAction.KeyCode.ESCAPE, null);
	setResizable(false);
	setClosable(true);
	setSizeUndefined();

	root = new VerticalLayout();

	// content.setSizeFull();

	TabSheet detailsWrapper = new TabSheet();
	// detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
	detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
	profiletab = buildProfileTab();
	detailsWrapper.addComponent(profiletab);

	// content.setExpandRatio(detailsWrapper, 1f);

	// preview.setSizeUndefined();
	HorizontalLayout HL = new HorizontalLayout(select);
	HL.setWidth(100, Unit.PERCENTAGE);
	panel = new Panel(preview);
	panel.setWidth(100.0f, Unit.PERCENTAGE);

	panel.setSizeFull();
	// preview.setHeight(57.0f, Sizeable.Unit.PERCENTAGE);
	preview.setWidth(100.0f, Unit.PERCENTAGE);
	// panel.setHeight(600.0f, Sizeable.Unit.PIXELS);

	footer = buildFooter();
	root.addComponents(detailsWrapper, panel, select, footer);
	root.setComponentAlignment(footer, Alignment.BOTTOM_CENTER);
	setContent(root);
	this.setResizeLazy(false);

	setWidth(400.0f, Unit.PIXELS);
	center();
	getUI().getCurrent().addWindow(this);
	focus();
	this.addCloseListener(new CloseListener() {
	    @Override
	    public void windowClose(CloseEvent closeEvent) {
		onClose();
	    }
	});

    }

    private Component buildFooter() {

	HorizontalLayout footer = new HorizontalLayout();
	footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
	footer.setWidth(100.0f, Unit.PERCENTAGE);

	done.setStyleName(ValoTheme.BUTTON_PRIMARY);
	done.addClickListener(clickEvent -> close());
	footer.addComponents( upload,downloadButton,delete,done);
	footer.setComponentAlignment(upload, Alignment.TOP_CENTER);
	footer.setComponentAlignment(delete, Alignment.TOP_LEFT);
	return footer;
    }

    private Component buildProfileTab() {
	HorizontalLayout profile = new HorizontalLayout();
	profile.setCaption("Imagenes");
	profile.setIcon(VaadinIcons.CAMERA);
	// root.setHeight(500.0f, Unit.PERCENTAGE);
	// root.setMargin(true);
	return profile;
    }


}
