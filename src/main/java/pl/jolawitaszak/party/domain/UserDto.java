package pl.jolawitaszak.party.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto {

    private Long userId;
    private String username;
    private String email;
    private Boolean attendingParty;
    private int phoneNumber;
}
