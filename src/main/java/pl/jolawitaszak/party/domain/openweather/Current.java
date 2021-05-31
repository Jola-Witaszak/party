package pl.jolawitaszak.party.domain.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {

    @JsonProperty(value = "dt")
    private int currentTime;

    @JsonProperty(value = "sunrise")
    private int sunrise;

    @JsonProperty(value = "sunset")
    private int sunset;

    @JsonProperty(value = "temp")
    private double temp;

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
    private List<Weather> weather;
}
