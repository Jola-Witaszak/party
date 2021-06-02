package pl.jolawitaszak.party.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.jolawitaszak.party.domain.openweather.Current;
import pl.jolawitaszak.party.domain.openweather.OpenWeatherDto;
import pl.jolawitaszak.party.domain.openweather.Weather;
import pl.jolawitaszak.party.external.api.openweather.client.OpenWeatherClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherServiceTest {

    @InjectMocks
    private OpenWeatherService openWeatherService;

    @Mock
    private OpenWeatherClient openWeatherClient;

    @Test
    void should() {
        //Given
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

        OpenWeatherDto openWeatherDto = new OpenWeatherDto(51.258, 19.369, "Europe/Warsaw", current);

        when(openWeatherClient.getWeatherForecast(51.258, 19.369)).thenReturn(openWeatherDto);

        //When
        OpenWeatherDto weatherForecast = openWeatherService.getWeatherForecast(51.258, 19.369);

        //Then
        assertEquals("Europe/Warsaw", weatherForecast.getTimeZone());
        assertEquals(17.11, weatherForecast.getCurrent().getTemp());
    }
}
