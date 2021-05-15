package pl.jolawitaszak.party.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "targets")
@NoArgsConstructor
@Getter
public class GpsPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gpsId;

    @NotNull
    private String placeName;

    @NotNull
    private double x;

    @NotNull
    private double y;

    @ManyToMany
    @JoinTable(
            name = "events_gps",
            joinColumns = {@JoinColumn(name = "gps_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events = new ArrayList<>();

    public GpsPosition(Long gpsId, @NotNull String placeName, @NotNull double x, @NotNull double y) {
        this.gpsId = gpsId;
        this.placeName = placeName;
        this.x = x;
        this.y = y;
    }
}
