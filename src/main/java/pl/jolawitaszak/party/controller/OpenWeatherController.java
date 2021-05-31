package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.jolawitaszak.party.domain.openweather.OpenWeatherDto;
import pl.jolawitaszak.party.service.GpsPositionNotFoundException;
import pl.jolawitaszak.party.service.OpenWeatherNotExistsException;
import pl.jolawitaszak.party.service.OpenWeatherService;

@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OpenWeatherController {

    private final OpenWeatherService openWeatherService;

    @GetMapping("/weather")
    public OpenWeatherDto get(@RequestParam double latitude, double longitude) throws OpenWeatherNotExistsException, GpsPositionNotFoundException {
        return openWeatherService.getWeatherForecast(latitude, longitude);
    }
}
