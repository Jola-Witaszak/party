package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Temperature {

    private float day;
    private float min;
    private float max;
    private float night;

    @JsonProperty(value = "eve")
    private float evening;

    @JsonProperty(value = "morn")
    private float morning;
}
