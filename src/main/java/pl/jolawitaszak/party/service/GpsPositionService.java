package pl.jolawitaszak.party.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jolawitaszak.party.domain.Event;
import pl.jolawitaszak.party.domain.GpsPosition;
import pl.jolawitaszak.party.domain.GpsPositionDto;
import pl.jolawitaszak.party.mapper.GpsPositionMapper;
import pl.jolawitaszak.party.repository.GpsPositionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GpsPositionService {

    private final GpsPositionRepository positionRepository;
    private final GpsPositionMapper positionMapper;

    public GpsPositionDto saveGpsPosition(final GpsPositionDto signalDto) {
        GpsPosition newGpsPosition = positionMapper.mapToGpsPosition(signalDto);
        GpsPosition savedGpsPosition = positionRepository.save(newGpsPosition);
        return positionMapper.mapToGpsPositionDto(savedGpsPosition);
    }

    public GpsPositionDto updatePosition(final GpsPositionDto signalDto) throws GpsPositionNotFoundException {
        Optional<GpsPosition> findSignal = positionRepository.findById(signalDto.getGpsId());
        if (findSignal.isPresent()) {
            GpsPosition positionToUpdate = positionMapper.mapToGpsPosition(signalDto);
            GpsPosition savedPosition = positionRepository.save(positionToUpdate);
            return positionMapper.mapToGpsPositionDto(savedPosition);
        } else {
            throw new GpsPositionNotFoundException("GpsSignal with id " + signalDto.getGpsId() + " not exists");
        }
    }

    public void removePosition(final long gpsSignalId) throws GpsPositionNotFoundException {
        GpsPosition findGpsPosition = positionRepository.findById(gpsSignalId).orElseThrow(() -> new GpsPositionNotFoundException("GpsSignal with id " + gpsSignalId + " not exists"));
        positionRepository.deleteById(findGpsPosition.getGpsId());
    }

    public List<GpsPositionDto> getAllGpsPositions() {
        List<GpsPosition> gpsPositions = positionRepository.findAll();
        return positionMapper.mapToGpsSignalsDtoList(gpsPositions);
    }

    public GpsPositionDto getGpsPosition(final long gpsSignalId) throws GpsPositionNotFoundException {
        GpsPosition findPosition = positionRepository.findById(gpsSignalId).orElseThrow(() -> new GpsPositionNotFoundException("GpsPosition with id " + gpsSignalId + " not exists"));
        return positionMapper.mapToGpsPositionDto(findPosition);
    }
}
