package moodle.mail;


import org.camunda.bpm.engine.variable.value.FileValue;

import java.io.InputStream;

public class Attachment {

    private final String fileName;
    private final String mimeType;
    private final InputStream fileContent;

    public Attachment(String fileName, String mimeType, InputStream fileValue) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.fileContent = fileValue;
    }

    public static Attachment fromFileValue(FileValue fileValue) {
        String filename = fileValue.getFilename();
        String mimetype = fileValue.getMimeType();
        InputStream fileContent = fileValue.getValue();

        return new Attachment(filename, mimetype, fileContent);
    }

    public InputStream getFileContent() {
        return fileContent;
    }

    public String getFilename() {
        return this.fileName;
    }

    public String getMimeType() {
        return this.mimeType;
    }

}
