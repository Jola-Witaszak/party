package pl.jolawitaszak.party.service;

public class OpenWeatherNotExistsException extends Exception {
    public OpenWeatherNotExistsException(String message) {
        super(message);
    }
}
