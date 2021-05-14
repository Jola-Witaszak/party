package pl.jolawitaszak.party.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @NotNull
    private String name;

    @NotNull
    private LocalDateTime startDate;

    private LocalDate endDate;

    private String description;

    @ManyToMany(
            mappedBy = "events",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    private Set<GpsPosition> gpsPositions = new HashSet<>();

    @ManyToMany(mappedBy = "events",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    private Set<User> users= new HashSet<>();

    public Event(Long eventId, @NotNull String name, @NotNull LocalDateTime startDate, LocalDate endDate, String description) {
        this.eventId = eventId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }
}
