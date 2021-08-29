package pl.jolawitaszak.party.external.api.openweather.model;
import lombok.Builder;
import lombok.Getter;
import pl.jolawitaszak.party.external.api.openweather.dto.AlertDto;
import pl.jolawitaszak.party.external.api.openweather.dto.DailyDto;

import java.time.Instant;
import java.util.List;

@Builder
@Getter
public class WeatherForecastDto {
    private final double latitude;
    private final double longitude;
    private final String timeZone;
    private final int timeZoneOffset;
    private final Instant sunrise;
    private final Instant sunset;
    private final double temperature;
    private final double feelsLike;
    private final int pressure;
    private final int humidity;
    private final double dewPoint;
    private final double indexUv;
    private final int clouds;
    private final double visibility;
    private final double windSpeed;
    private final String main;
    private final String description;
    private List<DailyDto> daily;

    private List<AlertDto> alerts;
}
