package fit.biesp.oneplan.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne
    @JoinColumn(name = "organiser_id")
    private UserEntity organiser;

    @ManyToMany
    private List<PersonEntity> attendees;

    /* TODO:
    @OneToMany
    private List<MoneyTransferEntity> moneyTransfer;

    @OneToMany
    private List<InvitationEntity> invitation; */


    private String name;
    private String description;
    private Date date;
    private Time time;
    private int capacity;

}