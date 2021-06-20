package pl.jolawitaszak.party.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.jolawitaszak.party.config.AdminConfig;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    public String buildWelcomeEmail(String message) {
        Context context = new Context();
        context.setVariable("adminConfig", adminConfig);
        context.setVariable("message", message);
        context.setVariable("app_url", "https://vast-peak-92879.herokuapp.com/");
        context.setVariable("button", "Party Fun & Spontan");
        context.setVariable("welcome_message", adminConfig.getWelcomeMessage());
        context.setVariable("goodbye_message", adminConfig.getGoodbyeMessage());

        return templateEngine.process("/mail/hello", context);
    }

    public String buildInvitationEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("welcome_message", adminConfig.getWelcomeMessage());
        context.setVariable("goodbye_message", adminConfig.getGoodbyeMessage());
        context.setVariable("button", "Party Fun & Spontan");
        context.setVariable("app_url", "https://vast-peak-92879.herokuapp.com/");

        return templateEngine.process("/mail/invitation-email", context);
    }
}
