package pl.jolawitaszak.party.external.api.openweather.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class OpenWeatherConfig {

    @Value(value = "${open.weather.api.endpoint.prod}")
    private String openWeatherEndpoint;

    @Value(value = "${open.weather.geocoding.api.endpoint}")
    private String geocodingEndpoint;

    @Value(value = "${open.weather.app.key}")
    private String appKey;
}
