package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentDto {

    @JsonProperty(value = "dt")
    private Instant currentTime;

    @JsonProperty(value = "sunrise")
    private Instant sunrise;

    @JsonProperty(value = "sunset")
    private Instant sunset;

    @JsonProperty(value = "temp")
    private double temperature;

    @JsonProperty(value = "feels_like")
    private double feelsLike;

    @JsonProperty(value = "pressure")
    private int pressure;

    @JsonProperty(value = "humidity")
    private int humidity;

    @JsonProperty(value = "dew_point")
    private double dewPoint;

    @JsonProperty(value = "uvi")
    private double indexUv;

    @JsonProperty(value = "clouds")
    private int clouds;

    @JsonProperty(value = "visibility")
    private double visibility;

    @JsonProperty(value = "wind_speed")
    private double windSpeed;

    @JsonProperty(value = "weather")
    private List<WeatherDto> weather;
}
