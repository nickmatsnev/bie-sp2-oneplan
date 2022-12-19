package fit.biesp.oneplan.service.event;

import fit.biesp.oneplan.exception.EventIsMissingException;
import fit.biesp.oneplan.model.EventModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class GeneralTest extends BaseTest {

    @Test
    public void createEventTest() {
        eventRepository.save(this.event);
        Mockito.verify(eventRepository).save(this.event);
        assertThat(this.event.getName()).isNotNull();
    }

    @Test
    public void deleteEventTest() throws EventIsMissingException {
        Mockito.when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        eventService.deleteEvent(event.getId());
        Mockito.verify(eventRepository).deleteById(event.getId());
    }

    @Test
    public void updateEventTest() {
        this.event.setName("updated");
        this.event = eventRepository.save(this.event);

        Mockito.verify(eventRepository).save(this.event);
        assertEquals(this.event.getName(), "updated");
    }

    @Test
    public void getEventTest() {
        var savedEvent = eventRepository.save(event);
        if(savedEvent.equals(event))
            Mockito.when(EventModel.fromModel(EventModel.toModel(event))).thenReturn(savedEvent);

        var getResult = EventModel.fromModel(EventModel.toModel(event));
        assertEquals(getResult.getName(), savedEvent.getName());
        assertEquals(getResult.getDescription(), savedEvent.getDescription());
        assertEquals(getResult.getDate(), savedEvent.getDate());
    }
}
