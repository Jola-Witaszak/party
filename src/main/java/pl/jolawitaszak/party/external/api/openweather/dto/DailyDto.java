package pl.jolawitaszak.party.external.api.openweather.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyDto {

    @JsonProperty(value = "dt")
    private Instant currentTime;

    private Instant sunrise;

    private Instant sunset;

    private Instant moonrise;

    private Instant moonset;

    @JsonProperty(value = "moon_phase")
    private float moonPhase;

    @JsonProperty(value = "temp")
    private Temperature temperature;

    private int pressure;

    private int humidity;

    @JsonProperty(value = "dew_point")
    private float dewPoint;

    @JsonProperty(value = "wind_speed")
    private float windSpeed;

    @JsonProperty(value = "wind_gust")
    private float windGust;

    private List<WeatherDto> weather;

    private int clouds;

    private float rain;

    @JsonProperty(value = "uvi")
    private float indexUv;
}
