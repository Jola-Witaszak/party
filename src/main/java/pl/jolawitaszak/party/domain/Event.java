package pl.jolawitaszak.party.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NotNull
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalTime startTime;

    private LocalDate endDate;

    private String description;

    @ManyToMany(
            mappedBy = "events",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<GpsPosition> gpsPositions = new HashSet<>();

    @ManyToMany(
            mappedBy = "events",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
           )
    private Set<User> users= new HashSet<>();

    public Event(Long eventId, @NotNull String name, @NotNull LocalDate startDate, @NotNull LocalTime startTime, LocalDate endDate, String description) {
        this.eventId = eventId;
        this.name = name;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.description = description;
    }
}
