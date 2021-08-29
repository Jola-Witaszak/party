package pl.jolawitaszak.party.external.api.openweather.dto;

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
public class OpenWeatherDto {

    @JsonProperty(value = "lat")
    private double latitude;

    @JsonProperty(value = "lon")
    private double longitude;

    @JsonProperty(value = "timezone")
    private String timeZone;

    @JsonProperty(value = "timezone_offset")
    private int timeZoneOffset;

    @JsonProperty(value = "current")
    private CurrentDto current;

    private List<DailyDto> daily;

    private List<AlertDto> alerts;
}
