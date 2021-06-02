package pl.jolawitaszak.party.external.api.openweather.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pl.jolawitaszak.party.domain.openweather.Current;
import pl.jolawitaszak.party.domain.openweather.OpenWeatherDto;
import pl.jolawitaszak.party.domain.openweather.Weather;
import pl.jolawitaszak.party.external.api.openweather.config.OpenWeatherConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenWeatherClientTest {

    @InjectMocks
    private OpenWeatherClient openWeatherClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OpenWeatherConfig openWeatherConfig;

    @Test
    void shouldFetchWeatherForecast() throws URISyntaxException {
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

        OpenWeatherDto openWeatherDto = new OpenWeatherDto(49.678, 18.804, "Europe/Warsaw", current);

        when(openWeatherConfig.getOpenWeatherEndpoint()).thenReturn("https://test.com");
        when(openWeatherConfig.getAppKey()).thenReturn("test_key");

        URI url = new URI("https://test.com?lat=49.678&lon=18.804&exclude=minutely,hourly&units=metric&lang=pl&appid=test_key");

        when(restTemplate.getForObject(url, OpenWeatherDto.class)).thenReturn(openWeatherDto);

        //When
        OpenWeatherDto fetchedWeatherForecast = openWeatherClient.getWeatherForecast(49.678, 18.804);

        //Then
        assertEquals("Europe/Warsaw", fetchedWeatherForecast.getTimeZone());
        assertEquals(59, fetchedWeatherForecast.getCurrent().getHumidity());
        assertEquals("całkowite zachmurzenie", fetchedWeatherForecast.getCurrent().getWeather().get(0).getDescription());
    }
}
