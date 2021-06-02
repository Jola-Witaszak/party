package pl.jolawitaszak.party.service;

public class GpsPositionNotFoundException extends Exception{
    public GpsPositionNotFoundException(String message) {
        super(message);
    }
}
