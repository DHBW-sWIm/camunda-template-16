package moodle;

import moodle.mail.Attachment;
import moodle.mail.MailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class MailMgmtStudentIsIll implements JavaDelegate {

  //    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

  public void execute(DelegateExecution execution) throws MessagingException {

    // [EXAMPLE] Get Camunda variables and work with them
    String studentName = (String) execution.getVariable("student_name");
    String studentMatnr = (String) execution.getVariable("student_matnr");
    String studentReason = (String) execution.getVariable("student_reason");
    Date sickUntilDate = (Date) execution.getVariable("student_length");

    // [EXAMPLE] setting a Email attachment from base64
    /*
     *   String base64 =
     *      "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WwPzeDUNiDNceu3JULR9s7gdr81QT56P4mqdFxhK3Y1ekvjveDYvZhWbrB3P8OD5r6YF0+uL2HzmFe6r6zR9wwB6EYGXpqKFDn2e6+mlHkD4LNuWLZ73y7pFze+Nj3UCEfJe2izN2MI81Q2YWl/AS4IgFUlRpB7o9t9ZZBXn7u/VQ4FLgF/qJXGEOD7gEgYhEUoiViETMiZiR8SjJXIiSKKsRO6QyFx9MkVMMW4FpboFaL5qg+UClmXkE7siGWEqEkisi9jVOhHe22QR4ZjuhDljxfyOW8hYGzF+srwinDO/Z88T48OR8jtP4pX1PHJf7Ckt7zwlYedJOZXzpLaV86ScqrA4dt1ztzQOejD3OTdXY3DE/Kp4tjTVXsP94U3jRCr6fwB/DbapCmVuZHN0cmVhbQplbmRvYmoKNiAwIG9iago8PC9UeXBlIC9Gb250Ci9CYXNlRm9udCAvSGVsdmV0aWNhLUJvbGQKL1N1YnR5cGUgL1R5cGUxCi9FbmNvZGluZyAvV2luQW5zaUVuY29kaW5nCi9Ub1VuaWNvZGUgNSAwIFIKPj4KZW5kb2JqCjcgMCBvYmoKPDwvVHlwZSAvRm9udAovQmFzZUZvbnQgL0hlbHZldGljYQovU3VidHlwZSAvVHlwZTEKL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcKL1RvVW5pY29kZSA1IDAgUgo+PgplbmRvYmoKOCAwIG9iago8PC9UeXBlIC9Gb250Ci9CYXNlRm9udCAvSGVsdmV0aWNhLU9ibGlxdWUKL1N1YnR5cGUgL1R5cGUxCi9FbmNvZGluZyAvV2luQW5zaUVuY29kaW5nCi9Ub1VuaWNvZGUgNSAwIFIKPj4KZW5kb2JqCjIgMCBvYmoKPDwKL1Byb2NTZXQgWy9QREYgL1RleHQgL0ltYWdlQiAvSW1hZ2VDIC9JbWFnZUldCi9Gb250IDw8Ci9GMSA2IDAgUgovRjIgNyAwIFIKL0YzIDggMCBSCj4+Ci9YT2JqZWN0IDw8Cj4+Cj4+CmVuZG9iago5IDAgb2JqCjw8Ci9Qcm9kdWNlciAoRlBERiAxLjgxKQovQ3JlYXRpb25EYXRlIChEOjIwMTkwNzA0MTIyNDIwKQo+PgplbmRvYmoKMTAgMCBvYmoKPDwKL1R5cGUgL0NhdGFsb2cKL1BhZ2VzIDEgMCBSCj4+CmVuZG9iagp4cmVmCjAgMTEKMDAwMDAwMDAwMCA2NTUzNSBmIAowMDAwMDAxNTk0IDAwMDAwIG4gCjAwMDAwMDI0NjcgMDAwMDAgbiAKMDAwMDAwMDAwOSAwMDAwMCBuIAowMDAwMDAwMDg3IDAwMDAwIG4gCjAwMDAwMDE2ODEgMDAwMDAgbiAKMDAwMDAwMjExNSAwMDAwMCBuIAowMDAwMDAyMjMzIDAwMDAwIG4gCjAwMDAwMDIzNDYgMDAwMDAgbiAKMDAwMDAwMjU5MSAwMDAwMCBuIAowMDAwMDAyNjY3IDAwMDAwIG4gCnRyYWlsZXIKPDwKL1NpemUgMTEKL1Jvb3QgMTAgMCBSCi9JbmZvIDkgMCBSCj4+CnN0YXJ0eHJlZgoyNzE3CiUlRU9GCg==";
     *   String filename = "test.pdf";
     *   String mimetype = "application/pdf";
     */
    String base64 = (String) execution.getVariable("base64");
    String filename = (String) execution.getVariable("filename");
    String mimetype = (String) execution.getVariable("mimetype");
    // TODO Create an Attachment of your file variables
    Attachment attachment = new Attachment(filename, mimetype, base64);

    // formatting and converting the date - plase use LocalDateTime for Date or Time related stuff
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDateTime localSickUntil = new Timestamp(sickUntilDate.getTime()).toLocalDateTime();
    String sickUntil = localSickUntil.format(formatter);

    String content =
        "<h1>Sie haben eine neue Krankschreibung erhalten</h1>"
            + "<p>Student: "
            + studentName
            + "</p>"
            + "<p>Matrikelnummer: "
            + studentMatnr
            + "</p>"
            + "<p>Grund der Krankschreibung: "
            + studentReason
            + "</p>"
            + "<p>Bis: "
            + sickUntil
            + "</p>"
            + "<a href='https://www.moodle.com'>Link (not working) zum Moodle Bestätigungsform</a>";

    // [EXAMPLE] setting a test variable
    execution.setVariable("testvar", true);

    // [EXAMPLE] Send Email with simple HTML Content
    String receiver = "test@test.com";
    String subject = "Test Mail";

    // [EXAMPLE] send the mail with two attachements
    new MailService()
        .createMessage(receiver, subject, content)
        .addAttachment(attachment)
        .addAttachment(attachment)
        .send();
  }
}
