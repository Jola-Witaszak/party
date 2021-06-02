package pl.jolawitaszak.party.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class AdminConfig {

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.mail}")
    private String adminMail;

    @Value("${admin.welcome.message}")
    private String welcomeMessage;

    @Value("${admin.goodbye.message}")
    private String GoodbyeMessage;
}
