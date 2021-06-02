package pl.jolawitaszak.party.service;

public class UserNotExistsException extends Exception{
    public UserNotExistsException(String message) {
        super(message);
    }
}
