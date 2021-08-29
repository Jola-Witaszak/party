package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WindDto {

    @JsonProperty(value = "speed")
    private float windSpeed;
}
