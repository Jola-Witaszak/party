package pl.jolawitaszak.party.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GpsPositionDto {

    private Long gpsId;
    private String placeName;
    private double x;
    private double y;
}
