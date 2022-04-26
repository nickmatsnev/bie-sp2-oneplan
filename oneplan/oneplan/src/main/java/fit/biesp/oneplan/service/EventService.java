package fit.biesp.oneplan.service;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.exception.EventAlreadyExistsException;
import fit.biesp.oneplan.exception.EventIsMissingException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void createEvent(EventEntity event) throws EventAlreadyExistsException, LocationIsMissingException {
        if (eventRepository.findById(event.getId()).isPresent())
            throw new EventAlreadyExistsException("Event already exists.");
        eventRepository.save(event);
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
        // TODO: update attendees (requires PersonModel)
    }

    public EventModel getEvent(Long id) throws EventIsMissingException {
        if (eventRepository.findById(id).isEmpty()) {
            throw new EventIsMissingException("Event with id " + id + " does not exist");
        }
        return EventModel.toModel(eventRepository.findById(id).get());
    }

}
