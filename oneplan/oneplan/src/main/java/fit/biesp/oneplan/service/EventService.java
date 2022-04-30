package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.exception.EventAlreadyExistsException;
import fit.biesp.oneplan.exception.EventIsMissingException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.PersonModel;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void createEvent(EventModel event) throws LocationIsMissingException {
        // removed chek for existing event as it is not necessarily unique
        eventRepository.save(EventModel.fromModel(event));
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
        event.setLocation(eventModel.getLocation());
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

}
