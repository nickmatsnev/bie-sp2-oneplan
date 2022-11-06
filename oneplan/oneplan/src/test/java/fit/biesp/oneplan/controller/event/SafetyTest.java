package fit.biesp.oneplan.controller.event;

import fit.biesp.oneplan.exception.EventIsMissingException;
import fit.biesp.oneplan.exception.LocationIsMissingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

public class SafetyTest extends BaseTest {

    @Test
    public void createEmptyEventTest() throws Exception {
        eventMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getIncorrectEventTest() throws Exception {
        when(eventService.getEvent(999L)).thenThrow(EventIsMissingException.class);

        eventMvc.perform(MockMvcRequestBuilders.get("/events/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteIncorrectEventTest() throws Exception {
        when(eventService.deleteEvent(999L)).thenThrow(EventIsMissingException.class);

        eventMvc.perform(MockMvcRequestBuilders
                        .delete("/events/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateIncorrectEventTest() throws Exception {
        when(eventService.updateEvent(updatedEventModel, 999L)).thenThrow(EventIsMissingException.class);

        eventMvc.perform(MockMvcRequestBuilders.put("/events/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
