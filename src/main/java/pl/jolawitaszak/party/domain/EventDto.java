package pl.jolawitaszak.party.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDate endDate;
    private String description;
}
