package pl.jolawitaszak.party.mapper;

import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.GpsPosition;
import pl.jolawitaszak.party.domain.GpsPositionDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GpsPositionMapper {

    public GpsPosition mapToGpsPosition(GpsPositionDto gpsPositionDto) {
        return new GpsPosition(
                gpsPositionDto.getGpsId(),
                gpsPositionDto.getPlaceName(),
                gpsPositionDto.getX(),
                gpsPositionDto.getY()
        );
    }

    public GpsPositionDto mapToGpsPositionDto(final GpsPosition gpsPosition) {
        return new GpsPositionDto(
                gpsPosition.getGpsId(),
                gpsPosition.getPlaceName(),
                gpsPosition.getX(),
                gpsPosition.getY()
        );
    }

    public Set<GpsPositionDto> mapToGpsPositionsDtoSet(Set<GpsPosition> gpsPositions) {
        return gpsPositions.stream()
                .map(this::mapToGpsPositionDto)
                .collect(Collectors.toSet());
    }

    public List<GpsPositionDto> mapToGpsPositionsDtoList(List<GpsPosition> gpsPositions) {
        return gpsPositions.stream()
                .map(this::mapToGpsPositionDto)
                .collect(Collectors.toList());
    }
}
