package pl.jolawitaszak.party.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Mail {

    private String mailTo;
    private String[] mailToCc;
    private String subject;
    private String message;
}
