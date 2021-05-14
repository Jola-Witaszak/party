package pl.jolawitaszak.party.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private String email;

    private Boolean attendingParty;

    @ManyToMany
    @JoinTable(name = "events_users",
               joinColumns = {@JoinColumn(name = "event_id")},
               inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<Event> events = new ArrayList<>();

    public User(Long userId, @NotNull String username, @NotNull String email, Boolean attendingParty) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.attendingParty = attendingParty;
    }
}
