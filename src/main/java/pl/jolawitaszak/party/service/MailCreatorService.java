package pl.jolawitaszak.party.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.jolawitaszak.party.config.AdminConfig;

@Service
public class MailCreatorService {

    @Qualifier("templateEngine")
    private final TemplateEngine templateEngine;

    private final AdminConfig adminConfig;

    public MailCreatorService(TemplateEngine templateEngine, AdminConfig adminConfig) {
        this.templateEngine = templateEngine;
        this.adminConfig = adminConfig;
    }

    public String buildWelcomeEmail(String message) {
        Context context = new Context();
        context.setVariable("adminConfig", adminConfig);
        context.setVariable("message", message);
        context.setVariable("app_url", "https://vast-peak-92879.herokuapp.com/");
        context.setVariable("button", "Party Fun & Spontan");
        context.setVariable("welcome_message", adminConfig.getWelcomeMessage());
        context.setVariable("goodbye_message", adminConfig.getGoodbyeMessage());

        return templateEngine.process("mail/hello", context);
    }

    public String buildInvitationEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("welcome_message", adminConfig.getWelcomeMessage());
        context.setVariable("goodbye_message", adminConfig.getGoodbyeMessage());
        context.setVariable("button_party_app", "Party Fun & Spontan");
        context.setVariable("button_yes", "Yes I will ! ");
        context.setVariable("button_no", "Not this time... ");
        context.setVariable("app_url", "https://vast-peak-92879.herokuapp.com/");

        return templateEngine.process("mail/invitation-email", context);
    }
}
