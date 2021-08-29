package pl.jolawitaszak.party.external.api.openweather.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class CityForecastDto {

    private final Instant date;
    private final String country;
    private final String city;
    private final float lon;
    private final float lat;
    private final String description;
    private final float temperature;
    private final float feelTemperature;
    private final float minTemperature;
    private final float maxTemperature;
    private final int pressure;
    private final int humidity;
    private final int visibility;
    private final float windSpeed;
    private final int cloudy;
    private final Instant sunrise;
    private final Instant sunset;

}
