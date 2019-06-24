package moodle;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import javax.mail.MessagingException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

public class MailMgmtStudentIsIll implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    public void execute(DelegateExecution execution) throws IOException {

        String studentName = (String) execution.getVariable("student_name");
        String studentMatnr = (String) execution.getVariable("student_matnr");
        String studentReason = (String) execution.getVariable("student_reason");
        Date studentLength = (Date) execution.getVariable("student_length");

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String sickUntil = dateFormat.format(studentLength);

        String content = "<h1>Sie haben eine neue Krankschreibung erhalten</h1>"
                + "<p>Student: " + studentName + "</p>"
                + "<p>Matrikelnummer: " + studentMatnr + "</p>"
                + "<p>Grund der Krankschreibung: " + studentReason + "</p>"
                + "<p>Bis: " + sickUntil + "</p>"
                + "<a href='www.moodle.com'>Link zum Moodle Bestätigungsform</a>";


        String receiver = "test@test.com";
        String subject = "Test Mail";
        try {
            Mail.send(receiver, subject, content);
        } catch (MessagingException e) {
//            TODO: refactor in own class
            LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                    + "processDefinitionId=" + execution.getProcessDefinitionId()
                    + ", activtyId=" + execution.getCurrentActivityId()
                    + ", activtyName='" + execution.getCurrentActivityName() + "'"
                    + ", processInstanceId=" + execution.getProcessInstanceId()
                    + ", businessKey=" + execution.getProcessBusinessKey()
                    + ", executionId=" + execution.getId()
                    + " \n\n"
                    + "STACKTRACE\n"
                    + Arrays.toString(e.getStackTrace()));
        }
    }
}
