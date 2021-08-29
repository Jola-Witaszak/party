package pl.jolawitaszak.party.external.api.openweather.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.jolawitaszak.party.external.api.openweather.dto.CityWeatherDto;
import pl.jolawitaszak.party.external.api.openweather.dto.OpenWeatherDto;
import pl.jolawitaszak.party.external.api.openweather.config.OpenWeatherConfig;
import pl.jolawitaszak.party.external.api.openweather.model.CityForecastDto;
import pl.jolawitaszak.party.external.api.openweather.model.WeatherForecastDto;

import java.net.URI;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class OpenWeatherClient {

    private final RestTemplate restTemplate;
    private final OpenWeatherConfig openWeatherConfig;

    public WeatherForecastDto getWeatherForecast(double lat, double lon) {

        URI url = getUriWithLanAndLon(lat, lon);
        OpenWeatherDto result = restTemplate.getForObject(url, OpenWeatherDto.class);

        return WeatherForecastDto.builder()
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
    }

    private URI getUriWithLanAndLon(double latitude, double longitude) {
        return UriComponentsBuilder.fromHttpUrl(openWeatherConfig.getOpenWeatherEndpoint() + "onecall")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("exclude", "minutely,hourly")
                .queryParam("units", "metric")
                .queryParam("lang", "pl")
                .queryParam("appid", openWeatherConfig.getAppKey())
                .build()
                .encode()
                .toUri();
    }

    public CityForecastDto getWeatherForCity(String city) {

        CityWeatherDto forecast =  callGetMethod("weather?q={city}&appid={openWeatherConfig.getAppKey()}&units=metric&lang=pl",
                CityWeatherDto.class,
                city, openWeatherConfig.getAppKey());


        return CityForecastDto.builder()
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
    }

    public  <T> T callGetMethod(String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(openWeatherConfig.getOpenWeatherEndpoint() + url,
                responseType, objects);
    }
}
