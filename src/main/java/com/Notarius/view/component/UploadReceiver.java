package com.Notarius.view.component;
import com.Notarius.services.Utils;
import com.vaadin.ui.Upload.Receiver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UploadReceiver implements Receiver {
    private static final long serialVersionUID = 2215337036540966711L;


    private OutputStream outputFile = null;
    private static final String directorioUpload = "Files";
    private String fullPath;
    private String filePath = directorioUpload + File.separator;
    private String fileName;
    private String fileType;
    private String fileExtension;

    public UploadReceiver() {
	File dir = new File(directorioUpload);
	dir.mkdir();
    }

    @Override
    public OutputStream receiveUpload(String strFilename, String strMIMEType) {
	File file = null;
	try {
	    this.setFullPath(filePath + strFilename);
	    // if(filePath.equals(System.getProperty("user.home"))){
	    // Long randomName=+Instant.now().toEpochMilli();
	    // this.setFullPath(System.getProperty("user.home")+randomName+".mv.db");
	    // this.setFileName(randomName.toString()+".mv.db");
	    // }



	    this.setFileName(strFilename);
	    this.setFileType(strMIMEType);

	    this.fileExtension = Utils.getFileExtension(strFilename);
	    fileName = Utils.removeFileExtension(strFilename);


	    file = new File(this.getFullPath());
	    if (!file.exists()) {

		file.createNewFile();
	    } else {
		while (file.exists()) {
		    strFilename = "copia_" + strFilename;
		    fileName = "copia_" + fileName;
		    this.setFullPath(filePath + strFilename);

		    file = new File(this.getFullPath());
		}

		file.createNewFile();
	    }
	    outputFile = new FileOutputStream(file);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return outputFile;
    }

    public String getFileExtension() {
	return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
	this.fileExtension = fileExtension;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFilePath(String filePath) {
	this.filePath = filePath;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public String getFileType() {
	return fileType;
    }

    public void setFileType(String fileType) {
	this.fileType = fileType;
    }

    public String getFilePath() {
	return filePath;
    }

    public OutputStream getOutputFile() {
	return outputFile;
    }

    public void setOutputFile(OutputStream outputFile) {
	this.outputFile = outputFile;
    }

    protected void finalize() {
	try {
	    super.finalize();
	    if (outputFile != null) {
		outputFile.close();
		outputFile = null;
	    }
	} catch (Throwable exception) {
	    exception.printStackTrace();
	}
    }

    /**
     * Devuelve path completo de un archivo. Path + filename + . + extension.
     * 
     * @return String con el path completo e.g. "../File/file.doc"
     */
    public String getFullPath() {
	return fullPath;
    }

    /**
     * Setea path completo de un archivo. "Path + filename + . + extension"
     */

    public void setFullPath(String fileName) {
	this.fullPath = fileName;
    }
}
