package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class MainDto {

    @JsonProperty(value = "temp")
    private float temperature;

    @JsonProperty(value = "feels_like")
    private float feelTemperature;

    @JsonProperty(value = "temp_min")
    private float minTemperature;

    @JsonProperty(value = "temp_max")
    private float maxTemperature;

    private int pressure;

    private int humidity;

}
