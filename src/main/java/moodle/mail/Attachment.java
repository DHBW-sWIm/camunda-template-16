package moodle.mail;


public class Attachment {

    private final String fileName;
    private final String mimeType;
    private final String base64;

    public Attachment(String fileName, String mimeType, String base64) {
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.base64 = base64;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String getBase64() {
        return this.base64;
    }
}
