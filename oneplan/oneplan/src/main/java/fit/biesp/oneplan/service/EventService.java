package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.exception.*;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.LocationModel;
import fit.biesp.oneplan.model.PersonModel;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private LocationService locationService;

    public String createEvent(EventModel event) throws LocationAlreadyExistsException, LocationIsMissingException {
        // removed chek for existing event as it is not necessarily unique
        // removed LocationIsMissingException as location is being created if missing
//        if (event.getLocation().getId() == null) {
//            var newLocation = locationService.postLocation(event.getLocation());
//            event.setLocation(newLocation);
//        }
        var message = "event created";//getExistingEvents(event);
        eventRepository.save(EventModel.fromModel(event));
        return message;
    }

    public void deleteEvent(Long id) throws EventIsMissingException {
        if (eventRepository.findById(id).isEmpty()) {
            throw new EventIsMissingException("Event with id " + id + " does not exist");
        }
        eventRepository.deleteById(id);
    }

    public void updateEvent(EventModel eventModel, Long id) throws EventIsMissingException {
        if (eventRepository.findById(id).isEmpty())
            throw new EventIsMissingException("Event with id " + id + " does not exist");

        EventEntity event = eventRepository.findById(id).get();
        event.setLocation(LocationModel.fromModel(eventModel.getLocation()));
        event.setName(eventModel.getName());
        event.setDescription(eventModel.getDescription());
        event.setDate(eventModel.getDate());
        event.setTime(eventModel.getTime());
        event.setCapacity(eventModel.getCapacity());
        event.renewAttendees(eventModel.getAttendees());
        eventRepository.save(event);
    }

    public EventModel getEvent(Long id) throws EventIsMissingException {
        if (eventRepository.findById(id).isEmpty()) {
            throw new EventIsMissingException("Event with id " + id + " does not exist");
        }
        return EventModel.toModel(eventRepository.findById(id).get());
    }

    private String getExistingEvents(EventModel event) throws LocationIsMissingException {
        StringBuilder message = new StringBuilder();
        for (var locationEvent : locationService.getEvents(event.getLocation().getName())) {
            if (Objects.equals(locationEvent.getDate().toString(), event.getDate().toString())) {
                if (message.isEmpty())
                    message.append("There are other events scheduled for this date at ").append(event.getLocation().getName()).append(": ");
                message.append("\nat "). append(locationEvent.getTime().toString());
            }
        }
        return message.toString();
    }

//    public Collection<EventModel> getUserEvents(Long organiserId) {
//        Set<EventModel> eventEntitySet = new HashSet<>();
//        return eventEntitySet;
//    }

}
