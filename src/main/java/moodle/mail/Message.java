package moodle.mail;

import org.apache.commons.io.IOUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.util.Properties;

public class Message {

    private MimeMessage message;
    private Multipart multipart;

    public Message(String receiver, String subject, String content, String SMTP_HOST, String SENDER) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", SMTP_HOST);
        properties.setProperty("mail.smtp.port", "1025");

        Session session = Session.getDefaultInstance(properties);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(SENDER));
        message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject(subject, "ISO-8859-1");

        BodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(content, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        this.message = message;
        this.multipart = multipart;
    }

    public Message addAttachment(Attachment attachment) throws MessagingException, IOException {
        BodyPart messageBodyPart = new MimeBodyPart();
        byte[] bytes = IOUtils.toByteArray(attachment.getFileContent());
        DataSource source = new ByteArrayDataSource(bytes, attachment.getMimeType());

        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(attachment.getFilename());

        this.multipart.addBodyPart(messageBodyPart);

        return this;
    }

    public void send() throws MessagingException {
        this.message.setContent(this.multipart);

        Transport.send(this.message);
    }

}
