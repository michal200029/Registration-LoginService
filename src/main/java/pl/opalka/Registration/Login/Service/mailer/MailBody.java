package pl.opalka.Registration.Login.Service.mailer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:email.properties")
public class MailBody {

    @Value("${email.confirmation.subject}")
    private String subject;

    @Value("${email.confirmation.text}")
    private String text;

    @Value("${email.confirmation.link}")
    private String link;

    public String getBody(String token){
        return text + link + token;
    }
}
