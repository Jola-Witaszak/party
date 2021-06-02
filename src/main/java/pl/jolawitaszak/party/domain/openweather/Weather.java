package pl.jolawitaszak.party.domain.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    @JsonProperty(value = "id")
    private long id;

    @JsonProperty(value = "main")
    private String main;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "icon")
    private String icon;
}
