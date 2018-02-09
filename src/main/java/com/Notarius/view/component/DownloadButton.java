package com.Notarius.view.component;


import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DownloadButton extends Button {
    StreamResource sr;
    FileDownloader fileDownloader;

    /**
     * El boton se crea por defecto deshabilitado hasta tanto no se le setee un
     * archivo para descargar.
     */
    public DownloadButton() {
	super();
	this.setIcon(VaadinIcons.DOWNLOAD);
	this.setEnabled(false);
    }



    public void setArchivoFromPath(String path, String filename) {
	if (fileDownloader == null) {

	    fileDownloader = new FileDownloader(fromPathtoSR(path, filename));
	    fileDownloader.extend(this);
	    this.setEnabled(true);
	} else {

	    fileDownloader.setFileDownloadResource(fromPathtoSR(path, filename));
	    this.setEnabled(true);
	}
    }


    private StreamResource fromPathtoSR(String path, String filename) {

	return new StreamResource(new StreamSource() {
	    public InputStream getStream() {
		InputStream is = null;
		try {
		    is = new FileInputStream(path + File.separator + filename);
		} catch (FileNotFoundException e) {
		    System.err.println("No se ha encontrado el archivo a descargar: " + path + File.separator
			    + filename);
		    e.printStackTrace();
		}
		return is;
	    }
	}, filename);

    }
}
