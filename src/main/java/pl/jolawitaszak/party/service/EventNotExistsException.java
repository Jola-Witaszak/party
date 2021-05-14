package pl.jolawitaszak.party.service;

public class EventNotExistsException extends Exception{
    public EventNotExistsException(String message) {
        super(message);
    }
}
