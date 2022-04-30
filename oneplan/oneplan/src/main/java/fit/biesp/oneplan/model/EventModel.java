package fit.biesp.oneplan.model;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
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

    public static EventModel toModel(EventEntity eventEntity) {
        var model = new EventModel();
        model.setId(eventEntity.getId());
        model.setLocation(LocationModel.toModel(eventEntity.getLocation()));
        model.setOrganiser(UserModel.toModel(eventEntity.getOrganiser()));
        model.setName(eventEntity.getName());
        model.setDescription(eventEntity.getDescription());
        model.setDate(eventEntity.getDate());
        model.setTime(eventEntity.getTime());
        model.setCapacity(eventEntity.getCapacity());
        for (var attendeeEntity : eventEntity.getAttendees())
            model.attendees.add(PersonModel.toModel(attendeeEntity));

        return model;
    }

    public static EventEntity fromModel(EventModel eventModel) {
        var entity = new EventEntity();
        entity.setId(eventModel.getId());
        entity.setName(eventModel.getName());
        entity.setLocation(LocationModel.fromModel(eventModel.getLocation()));
        entity.setOrganiser(UserModel.fromModel(eventModel.getOrganiser()));
        entity.setDescription(eventModel.getDescription());
        entity.setDate(eventModel.getDate());
        entity.setTime(eventModel.getTime());
        entity.setCapacity(eventModel.getCapacity());
        for (var attendeeModel : eventModel.getAttendees())
            entity.addAttendee(PersonModel.fromModel(attendeeModel));
        return entity;
    }

}
