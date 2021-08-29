package pl.jolawitaszak.party.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.jolawitaszak.party.external.api.openweather.dto.*;
import pl.jolawitaszak.party.external.api.openweather.client.OpenWeatherClient;
import pl.jolawitaszak.party.external.api.openweather.model.WeatherForecastDto;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherDtoServiceTest {

    @InjectMocks
    private OpenWeatherService openWeatherService;

    @Mock
    private OpenWeatherClient openWeatherClient;

    @Test
    void shouldFetchCurrentForecastAndForSevenDays() {
        //Given
        List<WeatherDto> weather = List.of(new WeatherDto(804, "Clouds",
                "całkowite zachmurzenie",
                "04d"));

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


        List<DailyDto> daily = List.of(new DailyDto(
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
                4.01f));

        List<AlertDto> alerts = List.of(new AlertDto(
                "IMGW-PIB Meteorological Forecast Centre, Poznan Team",
                "Yellow Rain warning",
                Instant.ofEpochSecond(1630303200),
                Instant.ofEpochSecond(1630378800),
                "It is forecasted moderate rainfall. The predicted sum of precipitation is from 30 mm to 50 mm. Possibility of thunderstorms.",
                 new String[]{"Rain"}
        ));

        OpenWeatherDto result = new OpenWeatherDto(51.6, 17.6, "Europe/Warsaw", 7200, current, daily, alerts);

        WeatherForecastDto weatherForecastDto = WeatherForecastDto.builder()
                .latitude(result.getLatitude())
                .longitude(result.getLongitude())
                .timeZone(result.getTimeZone())
                .timeZoneOffset(result.getTimeZoneOffset())
                .sunrise(result.getCurrent().getSunrise())
                .sunset(result.getCurrent().getSunset())
                .temperature(result.getCurrent().getTemperature())
                .feelsLike(result.getCurrent().getFeelsLike())
                .pressure(result.getCurrent().getPressure())
                .humidity(result.getCurrent().getHumidity())
                .dewPoint(result.getCurrent().getDewPoint())
                .indexUv(result.getCurrent().getIndexUv())
                .clouds(result.getCurrent().getClouds())
                .visibility(result.getCurrent().getVisibility())
                .windSpeed(result.getCurrent().getWindSpeed())
                .main(result.getCurrent().getWeather().get(0).getMain())
                .description(result.getCurrent().getWeather().get(0).getDescription())
                .daily(result.getDaily())
                .alerts(result.getAlerts())
                .build();

        when(openWeatherClient.getWeatherForecast(51.258, 19.369)).thenReturn(weatherForecastDto);

        //When
        WeatherForecastDto weatherForecast = openWeatherService.getWeatherForecast(51.258, 19.369);

        //Then
        assertEquals("Europe/Warsaw", weatherForecast.getTimeZone());
        assertEquals(17.11, weatherForecast.getTemperature());
    }
}
