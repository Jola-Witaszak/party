package pl.jolawitaszak.party.external.api.tomtom;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TomTomConfig {

    @Value("${tomtom.api.endpoint.prod}")
    private String tomtomApiEndpoint;

    @Value("${tomtom.app.key}")
    private String tomtomAppKey;
}
