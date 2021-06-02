package pl.jolawitaszak.party.domain;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Sms {

    private int smsTo;
    private String message;
    private String sender;
    private int msg_type;
    private int sandbox;
}
