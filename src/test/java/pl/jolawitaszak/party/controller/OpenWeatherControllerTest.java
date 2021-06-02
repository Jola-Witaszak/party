package pl.jolawitaszak.party.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jolawitaszak.party.domain.openweather.Current;
import pl.jolawitaszak.party.domain.openweather.OpenWeatherDto;
import pl.jolawitaszak.party.domain.openweather.Weather;
import pl.jolawitaszak.party.service.OpenWeatherService;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(OpenWeatherController.class)
class OpenWeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenWeatherService openWeatherService;

    @Test
    void shouldGetWeatherForecastForLocation() throws Exception {
        //
        List<Weather> weather = List.of(new Weather(804, "Clouds",
                "całkowite zachmurzenie",
                "04d"));
        Current current = new Current(1621772946,
                1621737670,
                1621795062,
                17.11,
                16.42,
                1014,
                59,
                9.04,
                3.89,
                100,
                10000,
                4.68, weather);
        OpenWeatherDto openWeatherDto = new OpenWeatherDto(49.678, 18.804, "Europe/Warsaw", current);

        when(openWeatherService.getWeatherForecast(51.258, 19.369)).thenReturn(openWeatherDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/weather?latitude=51.258&longitude=18.804"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
