package pl.jolawitaszak.party.external.api.openweather.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDto {

    private float lon;
    private float lat;
}
