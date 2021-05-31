package pl.jolawitaszak.party.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.openweather.OpenWeatherDto;
import pl.jolawitaszak.party.external.api.openweather.client.OpenWeatherClient;

@Service
@RequiredArgsConstructor
public class OpenWeatherService {

    private final OpenWeatherClient openWeatherClient;

    public OpenWeatherDto getWeatherForecast(double latitude, double longitude) {

        return openWeatherClient.getWeatherForecast(latitude, longitude);
    }
}
