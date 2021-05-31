package pl.jolawitaszak.party.controller;

import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jolawitaszak.party.domain.GpsPositionDto;
import pl.jolawitaszak.party.service.GpsPositionService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(GpsPositionController.class)
class GpsPositionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GpsPositionService gpsPositionService;

    @Test
    void shouldCreateGpsPosition() throws Exception {
        //Given
        GpsPositionDto gpsPositionDto = new GpsPositionDto(null, "Czantoria", 52.569, 18.369);
        GpsPositionDto createdGpsPositionDto = new GpsPositionDto(1L, "Czantoria", 52.569, 18.369);

        when(gpsPositionService.saveGpsPosition(any(GpsPositionDto.class))).thenReturn(createdGpsPositionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(gpsPositionDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/gps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.x", Matchers.is(52.569)));
    }

    @Test
    void shouldUpdateGpsPosition() throws Exception {
        //Given
        GpsPositionDto createdGpsPositionDto = new GpsPositionDto(1L, "Czantoria", 49.678, 18.804);

        when(gpsPositionService.updatePosition(any(GpsPositionDto.class))).thenReturn(createdGpsPositionDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(createdGpsPositionDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/v1/gps")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.placeName", Matchers.is("Czantoria")));
    }

    @Test
    void shouldRemoveGpsPosition() throws Exception {
        //Given
        GpsPositionDto gpsPositionDto = new GpsPositionDto(1L, "Czantoria", 49.678, 18.804);

        doNothing().when(gpsPositionService).removePosition(gpsPositionDto.getGpsId());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .delete("/v1/gps/1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void shouldFetchOneGpsPosition() throws Exception {
        //Given
        GpsPositionDto gpsPositionDto = new GpsPositionDto(1L, "Czantoria", 49.678, 18.804);

        when(gpsPositionService.getGpsPosition(gpsPositionDto.getGpsId())).thenReturn(gpsPositionDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/gps/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.placeName", Matchers.is("Czantoria")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gpsId", Matchers.is(1)));
    }

    @Test
    void shouldGetAllGpsPositions() throws Exception {
        //Given
        List<GpsPositionDto> gpsPositions = List.of(
        new GpsPositionDto(1L, "Czantoria", 49.678, 18.804),
        new GpsPositionDto(1L, "Westerplatte", 54.40667, 18.66694)
        );

        when(gpsPositionService.getAllGpsPositions()).thenReturn(gpsPositions);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/gps")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }
}
