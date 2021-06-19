package pl.jolawitaszak.party.external.api.gsmservicepl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GsmServiceConfig {

    @Value(value = "${gsm.service.base.url}")
    private  String baseUrl;

    @Value(value = "${gsm.service.login}")
    private String login;

    @Value(value = "${gsm.service.password}")
    private String password;
}
