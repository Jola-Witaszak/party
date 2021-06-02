package pl.jolawitaszak.party.domain.openweather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpenWeather {

    private double lat;
    private double lon;
    private String timeZone;
    private Current current;
}
