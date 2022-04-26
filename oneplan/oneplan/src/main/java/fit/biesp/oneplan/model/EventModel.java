package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
public class EventModel {
    private Long id;
    private LocationEntity location;
    private Long organiserId;
//    private List<PersonModel> attendees;
    private String name;
    private String description;
    private Date date;
    private Time time;
    private int capacity;

    public static EventModel toModel(EventEntity eventEntity) {
        var model = new EventModel();
        model.setId(eventEntity.getId());
        model.setLocation(eventEntity.getLocation());
        model.setOrganiserId(eventEntity.getOrganiser().getId());
        model.setName(eventEntity.getName());
        model.setDescription(eventEntity.getDescription());
        model.setDate(eventEntity.getDate());
        model.setTime(eventEntity.getTime());
        // TODO: Set attendees

        return model;
    }
}
