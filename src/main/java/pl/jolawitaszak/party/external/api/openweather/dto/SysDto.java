package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SysDto {

    @JsonProperty(value = "country")
    private String countryCode;
    private Instant sunrise;
    private Instant sunset;
}
