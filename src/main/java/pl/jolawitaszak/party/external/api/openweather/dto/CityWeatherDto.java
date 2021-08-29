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
public class CityWeatherDto {

    @JsonProperty(value = "coord")
    private CoordinatesDto coordinatesDto;
    private List<WeatherDto> weather;
    private MainDto main;
    private int visibility;

    @JsonProperty(value = "wind")
    private WindDto windDto;
    private CloudsDto cloudsDto;

    @JsonProperty(value = "dt")
    private Instant date;
    private SysDto sys;

    @JsonProperty(value = "timezone")
    private int timeZone;

    @JsonProperty(value = "name")
    private String city;

}
