package pl.jolawitaszak.party.external.api.openweather.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OpenWeatherConfig {

    @Value("${open.weather.api.endpoint.prod}")
    private String openWeatherEndpoint;

    @Value("${open.weather.geocoding.api.endpoint}")
    private String geocodingEndpoint;

    @Value("${open.weather.app.key}")
    private final String appKey = "104073d14df9222695cae72d507ab9ff";
}
