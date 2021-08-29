package pl.jolawitaszak.party.external.api.openweather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlertDto {

    @JsonProperty(value = "sender_name")
    private String alertSender;

    private String event;

    @JsonProperty("start")
    private Instant startAlert;

    @JsonProperty(value = "end")
    private Instant endAlert;

    private String description;

    private String[] tags;
}
