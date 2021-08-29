package pl.jolawitaszak.party.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.jolawitaszak.party.external.api.openweather.dto.*;
import pl.jolawitaszak.party.external.api.openweather.model.CityForecastDto;
import pl.jolawitaszak.party.external.api.openweather.model.WeatherForecastDto;
import pl.jolawitaszak.party.service.OpenWeatherService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(OpenWeatherController.class)
class OpenWeatherDtoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenWeatherService openWeatherService;

    @Test
    void shouldGetWeatherForecastForLocation() throws Exception {
        //
        CoordinatesDto coordinatesDto = new CoordinatesDto(18.56f, 54.4418f);

        List<WeatherDto> weather = List.of(new WeatherDto(804, "Clouds",
                "całkowite zachmurzenie",
                "04d"));

        MainDto main = new MainDto(
                16.71f,
                16.58f,
                14.84f,
                21.05f,
                1013,
                82
        );

        WindDto wind = new WindDto(4.63f);

        CloudsDto cloudsDto = new CloudsDto(75);

        SysDto sys = new SysDto(
                "PL",
                Instant.ofEpochSecond(1630208877),
                Instant.ofEpochSecond(1630259152)
        );

        CityWeatherDto forecast = new CityWeatherDto(
                coordinatesDto,
                weather,
                main,
                10000,
                wind,
                cloudsDto,
                Instant.ofEpochSecond(1630245205),
                sys,
                7200,
                "Sopot"
        );

        CityForecastDto cityForecastDto = CityForecastDto.builder()
                .country(forecast.getSys().getCountryCode())
                .date(forecast.getDate())
                .description(forecast.getWeather().get(0).getDescription())
                .temperature(forecast.getMain().getTemperature())
                .feelTemperature(forecast.getMain().getFeelTemperature())
                .minTemperature(forecast.getMain().getMinTemperature())
                .maxTemperature(forecast.getMain().getMaxTemperature())
                .pressure(forecast.getMain().getPressure())
                .humidity(forecast.getMain().getHumidity())
                .visibility(forecast.getVisibility())
                .windSpeed(forecast.getWindDto().getWindSpeed())
                .cloudy(forecast.getCloudsDto().getCloudy())
                .sunrise(forecast.getSys().getSunrise())
                .sunset(forecast.getSys().getSunset())
                .city(forecast.getCity())
                .lon(forecast.getCoordinatesDto().getLon())
                .lat(forecast.getCoordinatesDto().getLat())
                .build();

        when(openWeatherService.getWeatherInCity("Sopot")).thenReturn(cityForecastDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/weather/Sopot"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country").value("PL"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
