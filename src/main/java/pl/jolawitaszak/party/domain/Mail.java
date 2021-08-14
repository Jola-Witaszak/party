package pl.jolawitaszak.party.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    private String mailTo;
    private String[] mailToCc;
    private String subject;
    private String message;
}
