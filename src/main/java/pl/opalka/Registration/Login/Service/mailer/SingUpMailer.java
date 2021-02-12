package pl.opalka.Registration.Login.Service.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SingUpMailer {

    private JavaMailSender javaMailSender;
    private MailBody mailBody;

    @Autowired
    public SingUpMailer(JavaMailSender javaMailSender, MailBody mailBody) {
        this.javaMailSender = javaMailSender;
        this.mailBody = mailBody;
    }

    public void sendConfirmationLink(String receiver, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiver);
        message.setText(mailBody.getBody(token));
        message.setSubject(mailBody.getSubject());
        javaMailSender.send(message);
    }
}
