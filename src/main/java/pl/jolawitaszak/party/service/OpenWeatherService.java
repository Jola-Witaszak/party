package pl.jolawitaszak.party.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.external.api.openweather.dto.OpenWeatherDto;
import pl.jolawitaszak.party.external.api.openweather.client.OpenWeatherClient;
import pl.jolawitaszak.party.external.api.openweather.model.CityForecastDto;
import pl.jolawitaszak.party.external.api.openweather.model.WeatherForecastDto;

@Service
@RequiredArgsConstructor
public class OpenWeatherService {

    private final OpenWeatherClient openWeatherClient;

    public WeatherForecastDto getWeatherForecast(double latitude, double longitude) {

        return openWeatherClient.getWeatherForecast(latitude, longitude);
    }

    public CityForecastDto getWeatherInCity(String city) {
        return openWeatherClient.getWeatherForCity(city);
    }
}
