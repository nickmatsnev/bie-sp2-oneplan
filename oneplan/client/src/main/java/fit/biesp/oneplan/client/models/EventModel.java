package fit.biesp.oneplan.client.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventModel {
    private Long id;
    private LocationModel location;
    private UserModel organiser;
    private List<PersonModel> attendees;
    private String name, description;
    private Date date;
    private Time time;
    private int capacity;

}
