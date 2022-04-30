package fit.biesp.oneplan.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "events")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private UserEntity organiser;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "persons_events", joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"))
    private List<PersonEntity> attendees;

    /* TODO:
    @OneToMany
    private List<MoneyTransferEntity> moneyTransfer;

    @OneToMany
    private List<InvitationEntity> invitation; */

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "date")
    private Date date;

    @NotNull
    @Column(name = "time")
    private Time time;

    @NotNull
    @Column(name = "capacity")
    private int capacity;

    public void addAttendee(PersonEntity person) {
        attendees.add(person);
    }
}