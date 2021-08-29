package pl.jolawitaszak.party.external.api.openweather.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pl.jolawitaszak.party.external.api.openweather.dto.*;
import pl.jolawitaszak.party.external.api.openweather.config.OpenWeatherConfig;
import pl.jolawitaszak.party.external.api.openweather.model.WeatherForecastDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherDtoClientTest {

    @InjectMocks
    private OpenWeatherClient openWeatherClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OpenWeatherConfig openWeatherConfig;

    @Test
    void shouldFetchWeatherForecastForSevenDays() throws URISyntaxException {
        //Given
        List<WeatherDto> weather = List.of(
                new WeatherDto(804, "Clouds", "całkowite zachmurzenie", "04d"));

        CurrentDto current = new CurrentDto(
                Instant.ofEpochSecond(1630236057),
                Instant.ofEpochSecond(1630209449),
                Instant.ofEpochSecond(1630259041),
                17.11,
                16.42,
                1014,
                59,
                9.04,
                3.89,
                100,
                10000,
                4.68, weather);

        Temperature temperature = new Temperature(16.06f,
                12.65f,
                16.06f,
                12.72f,
                13.83f,
                12.65f);


        List<DailyDto> daily = List.of(
                new DailyDto(
                    Instant.ofEpochSecond(1630317600),
                    Instant.ofEpochSecond(1630295945),
                    Instant.ofEpochSecond(1630345308),
                    Instant.ofEpochSecond(1630356780),
                    Instant.ofEpochSecond(1630327800),
                    0.75f,
                    temperature,
                    1013,
                    78,
                    12.5f,
                    1.5f,
                    3.5f,
                    weather,
                    60,
                    2.5f,
                    4.01f),
                new DailyDto(
                        Instant.ofEpochSecond(1630472400),
                        Instant.ofEpochSecond(1630447402),
                        Instant.ofEpochSecond(1630498426),
                        Instant.ofEpochSecond(1630508580),
                        Instant.ofEpochSecond(1630491420),
                        0.81f,
                        temperature,
                        1011,
                        40,
                        1.76f,
                        2f,
                        5.03f,
                        weather,
                        88,
                        5.9f,
                        3.22f
                )
                );

        List<AlertDto> alerts = List.of(new AlertDto(
                "IMGW-PIB Meteorological Forecast Centre, Poznan Team",
                "Yellow Rain warning",
                Instant.ofEpochSecond(1630303200),
                Instant.ofEpochSecond(1630378800),
                "It is forecasted moderate rainfall. The predicted sum of precipitation is from 30 mm to 50 mm. Possibility of thunderstorms.",
                new String[]{"Rain"}
        ));

        OpenWeatherDto openWeatherDto = new OpenWeatherDto(51.6, 17.6, "Europe/Warsaw", 7200, current, daily, alerts);

        when(openWeatherConfig.getOpenWeatherEndpoint()).thenReturn("https://test.com/");
        when(openWeatherConfig.getAppKey()).thenReturn("test_key");

        URI url = new URI(
                "https://test.com/onecall?lat=49.678&lon=18.804&exclude=minutely,hourly&units=metric&lang=pl&appid=test_key");
        when(restTemplate.getForObject(url, OpenWeatherDto.class)).thenReturn(openWeatherDto);


        //When
        WeatherForecastDto fetchedWeatherForecast = openWeatherClient.getWeatherForecast(49.678, 18.804);

        //Then
        assertEquals("Europe/Warsaw", fetchedWeatherForecast.getTimeZone());
        assertEquals(59, fetchedWeatherForecast.getHumidity());
        assertEquals("całkowite zachmurzenie", fetchedWeatherForecast.getDescription());
        assertEquals(2, fetchedWeatherForecast.getDaily().size());
    }
}
