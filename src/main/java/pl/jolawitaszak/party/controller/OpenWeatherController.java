package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jolawitaszak.party.external.api.openweather.dto.OpenWeatherDto;
import pl.jolawitaszak.party.external.api.openweather.model.CityForecastDto;
import pl.jolawitaszak.party.external.api.openweather.model.WeatherForecastDto;
import pl.jolawitaszak.party.service.OpenWeatherService;

@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OpenWeatherController {

    private final OpenWeatherService openWeatherService;

    @GetMapping("/weather")
    public WeatherForecastDto get(@RequestParam double latitude, double longitude) {
        return openWeatherService.getWeatherForecast(latitude, longitude);
    }

    @GetMapping(value = "/weather/{city}")
    public CityForecastDto getWeatherForCity(@PathVariable String city) {
        return openWeatherService.getWeatherInCity(city);
    }
}
