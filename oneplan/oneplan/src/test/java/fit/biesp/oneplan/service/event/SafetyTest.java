package fit.biesp.oneplan.service.event;

import fit.biesp.oneplan.exception.EventIsMissingException;
import fit.biesp.oneplan.model.EventModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class SafetyTest extends BaseTest {

    @Test
    public void createInvalidEventTest() {
        eventRepository.save(null);
        Mockito.verify(eventRepository).save(null);
    }

    @Test
    public void deleteInvalidEventTest() throws EventIsMissingException {
        Assertions.assertThrows(EventIsMissingException.class,
                () -> eventService.deleteEvent(event.getId()));
    }

    @Test
    public void updateMissingEventTest() {
        Assertions.assertThrows(EventIsMissingException.class,
                () -> eventService.updateEvent(EventModel.toModel(event), event.getId()));
    }

    @Test
    public void getMissingEventTest() {
        Assertions.assertThrows(EventIsMissingException.class,
                () -> eventService.getEvent(event.getId()));
    }
}
