package fit.biesp.oneplan.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.stereotype.Service;

import java.io.IOException;

// we use sendgrip for mail service
public class MailService {

    public static void sendEmail(String toEmail, String linkToInvite) throws IOException {
        Email from = new Email("matsnnik@fit.cvut.cz");
        String subject = "Sending Invitation for Friendship";
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Become my friend, i will pass parameters later.\n" +
                linkToInvite + "\n thank you for accepting it.");
        Mail mail = new Mail(from, subject, to, content);
        // our api key
        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }
}

