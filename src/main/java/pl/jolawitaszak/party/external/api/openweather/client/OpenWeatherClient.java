package pl.jolawitaszak.party.external.api.openweather.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jolawitaszak.party.domain.openweather.OpenWeatherDto;
import pl.jolawitaszak.party.external.api.openweather.config.OpenWeatherConfig;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final RestTemplate restTemplate;
    private final OpenWeatherConfig openWeatherConfig;

    public OpenWeatherDto getWeatherForecast(double latitude, double longitude ) {

         URI url = UriComponentsBuilder.fromHttpUrl(openWeatherConfig.getOpenWeatherEndpoint())
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("exclude", "minutely,hourly")
                .queryParam("units", "metric")
                .queryParam("lang", "pl")
                .queryParam("appid", openWeatherConfig.getAppKey())
                .build()
                .encode()
                .toUri();

         return restTemplate.getForObject(url, OpenWeatherDto.class);
    }
}
