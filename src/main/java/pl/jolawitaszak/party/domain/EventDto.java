package pl.jolawitaszak.party.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDto {

    private Long eventId;
    private String name;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private String description;
}
