package pl.jolawitaszak.party.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.jolawitaszak.party.domain.GpsPosition;
import pl.jolawitaszak.party.domain.GpsPositionDto;
import pl.jolawitaszak.party.mapper.GpsPositionMapper;
import pl.jolawitaszak.party.repository.GpsPositionRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GpsPositionServiceTestSuite {

    @Autowired
    private GpsPositionRepository gpsPositionRepository;

    @Autowired
    private GpsPositionService gpsPositionService;

    @Autowired
    private GpsPositionMapper gpsPositionMapper;

    @Test
    void shouldAddGpsPosition() {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPositionDto gpsPositionDto = gpsPositionMapper.mapToGpsPositionDto(gpsPosition);

        //When
        GpsPositionDto savedGps = gpsPositionService.saveGpsPosition(gpsPositionDto);
        long gpsId = savedGps.getGpsId();

        //Then
        assertTrue(gpsPositionRepository.existsById(gpsId));

        //CleanUp
        gpsPositionRepository.deleteById(gpsId);
    }

    @Test
    void shouldUpdateGpsPosition() throws GpsPositionNotFoundException {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        long gpsId = savedGps.getGpsId();

        GpsPositionDto gpsDto = new GpsPositionDto(gpsId, "Home sweet Home", 51.55, 17.20);

        //When
        GpsPositionDto updatedPosition = gpsPositionService.updatePosition(gpsDto);

        //Then
        assertNotEquals(gpsPosition.getPlaceName(), updatedPosition.getPlaceName());
        assertEquals(gpsDto.getGpsId(), updatedPosition.getGpsId());
        assertNotEquals(gpsPosition.getX(), updatedPosition.getX());
        assertNotEquals(gpsPosition.getY(), updatedPosition.getY());

        //CleanUp
        gpsPositionRepository.deleteById(gpsId);
    }

    @Test
    void shouldThrowExceptionWhenUpdateGpsPositionWithIncorrectPositionId() {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        long gpsId = savedGps.getGpsId();

        GpsPositionDto positionToUpdate = new GpsPositionDto(gpsId, "Mount Everest", 51.25, 17.56);
        gpsPositionRepository.deleteById(gpsId);

        //When & Then
        assertThrows(GpsPositionNotFoundException.class, () -> gpsPositionService.updatePosition(positionToUpdate));
    }

    @Test
    void shouldRemoveGpsPosition() throws GpsPositionNotFoundException {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition gpsPosition2 = new GpsPosition(null, "test place",51.25, 17.56);

        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        GpsPosition savedPosition2 = gpsPositionRepository.save(gpsPosition2);

        long gpsId = savedGps.getGpsId();
        long gpsId2 = savedPosition2.getGpsId();

        int gpsListSize = gpsPositionRepository.findAll().size();

        //When
        gpsPositionService.removePosition(gpsId);

        //Then
        int gpsListSizeAfterRemoval = gpsPositionRepository.findAll().size();

        assertNotEquals(gpsListSize, gpsListSizeAfterRemoval);

        //CleanUp
        gpsPositionRepository.deleteById(gpsId2);
    }

    @Test
    void itDoesNotThrowExceptionWhenRemoveGpsPositionWithCorrectId() {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        long gpsId = savedGps.getGpsId();

        //When & Then
        assertDoesNotThrow(() -> gpsPositionService.removePosition(gpsId));
    }

    @Test
    void shouldFetchAllGpsPositions() {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition gpsPosition2 = new GpsPosition(null, "test place",51.25, 17.56);

        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        GpsPosition savedGps2 = gpsPositionRepository.save(gpsPosition2);

        long gpsId = savedGps.getGpsId();
        long gpsId2 = savedGps2.getGpsId();

        int gpsPositionsListSize = gpsPositionRepository.findAll().size();

        //When
        List<GpsPositionDto> fetchedGps = gpsPositionService.getAllGpsPositions();
        int fetchedGpsListSize = fetchedGps.size();

        //Then
        assertEquals(gpsPositionsListSize, fetchedGpsListSize);

        //CleanUp
        gpsPositionRepository.deleteById(gpsId);
        gpsPositionRepository.deleteById(gpsId2);
    }
    @Test
    void shouldFetchOneGpsPosition() throws GpsPositionNotFoundException {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        long gpsId = savedGps.getGpsId();

        //When
        GpsPositionDto fetchedGpsDto = gpsPositionService.getGpsPosition(gpsId);
        GpsPosition fetchedGps = gpsPositionMapper.mapToGpsPosition(fetchedGpsDto);

        //Then
        assertEquals("test place", fetchedGps.getPlaceName());
        assertEquals(51.25, fetchedGps.getX());
        assertEquals(17.56, fetchedGps.getY());

        //CleanUp
        gpsPositionRepository.deleteById(gpsId);
    }
    @Test
    void shouldThrowExceptionWhenGetGpsPositionWithIncorrectId() {
        //Given
        GpsPosition gpsPosition = new GpsPosition(null, "test place",51.25, 17.56);
        GpsPosition savedGps = gpsPositionRepository.save(gpsPosition);
        long gpsId = savedGps.getGpsId();
        gpsPositionRepository.deleteById(gpsId);

        //When & Then
        assertThrows(GpsPositionNotFoundException.class, () -> gpsPositionService.getGpsPosition(gpsId));
    }
}
