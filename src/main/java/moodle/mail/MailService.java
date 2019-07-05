package moodle.mail;

import javax.mail.MessagingException;

public class MailService {

    // TODO:  Change the default values on demand
    // Default Value for SMTP Host
    private static final String DEFAULT_SMTP_HOST = "swim_bpxtest_mailhog";
    // Default Value for email address.
    private static final String DEFAULT_SENDER = "noreply@moodle-dhbw.de";

    //Value for SMTP_HOST
    private final String SMTP_HOST;
    //Value for email address. Be aware, that the email address does not exists, which should be made clear.
    private final String SENDER;

    public MailService() {
        this.SMTP_HOST = MailService.DEFAULT_SMTP_HOST;
        this.SENDER = MailService.DEFAULT_SENDER;
    }

    public MailService(final String SMTP_HOST, final String SENDER) {
        this.SMTP_HOST = SMTP_HOST;
        this.SENDER = SENDER;
    }

    public Message createMessage(String receiver, String subject, String content) throws MessagingException {
        return new Message(receiver, subject, content, SMTP_HOST, SENDER);
    }


}
