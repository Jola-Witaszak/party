package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    @JsonProperty(value = "id")
    private long id;

    @JsonProperty(value = "main")
    private String main;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "icon")
    private String icon;
}
