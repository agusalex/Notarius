package com.Notarius.view.misc;

/**
 * Created by Hugo on 20/06/2017.
 */

import com.vaadin.ui.Upload.Receiver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;

public class UploadReceiver implements Receiver {
    private static final long serialVersionUID = 2215337036540966711L;
    OutputStream outputFile = null;

    private String fileName;

    /*
    public UploadReceiver(String fileName)

    {
        this.fileName = fileName;
    }
*/
    @Override
    public OutputStream receiveUpload(String strFilename, String strMIMEType) {
        File file=null;
        try {
            this.setFileName(strFilename);

            file = new File(this.getFileName());

            if(!file.exists()) {
                file.createNewFile();
            }
            outputFile =  new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    protected void finalize() {
        try {
            super.finalize();
            if(outputFile!=null) {
                outputFile.close();
            }
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
