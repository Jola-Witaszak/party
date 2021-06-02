package pl.jolawitaszak.party.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.jolawitaszak.party.domain.GpsPositionDto;
import pl.jolawitaszak.party.service.GpsPositionNotFoundException;
import pl.jolawitaszak.party.service.GpsPositionService;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class GpsPositionController {

    private final GpsPositionService gpsPositionService;

    @PostMapping(value = "/gps", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GpsPositionDto save(@RequestBody GpsPositionDto positionDto) {
        return gpsPositionService.saveGpsPosition(positionDto);
    }

    @PutMapping(value = "/gps", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GpsPositionDto update(@RequestBody GpsPositionDto positionDto) throws GpsPositionNotFoundException {
        return gpsPositionService.updatePosition(positionDto);
    }

    @DeleteMapping(value = "/gps/{gpsPositionId}")
    public void delete(@PathVariable long gpsPositionId) throws GpsPositionNotFoundException {
        gpsPositionService.removePosition(gpsPositionId);
    }

    @GetMapping(value = "/gps/{gpsPositionId}")
    public GpsPositionDto getPosition(@PathVariable long gpsPositionId) throws GpsPositionNotFoundException {
        return gpsPositionService.getGpsPosition(gpsPositionId);
    }

    @GetMapping(value = "/gps")
    public List<GpsPositionDto> getAll() {
        return gpsPositionService.getAllGpsPositions();
    }
}
