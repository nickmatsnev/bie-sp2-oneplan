package fit.biesp.oneplan.controller.event;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.EventModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest {

    @Test
    public void createEventTest() throws Exception {
        Mockito.when(eventService.createEvent(eventModel))
                .thenReturn("Event successfully created");

        eventMvc.perform(MockMvcRequestBuilders.post("/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getEventTest() throws Exception {
        Mockito.when(eventService.getEvent(1L))
                .thenReturn(eventModel);

        eventMvc.perform(MockMvcRequestBuilders.get("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(eventModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(eventModel.getName())));
    }

    @Test
    public void deleteEventTest() throws Exception {
        var response = eventMvc.perform(MockMvcRequestBuilders
                        .delete("/events/{id}", 1L))
                .andReturn();

        assertEquals(response.getResponse().getStatus(), 200);
        assertEquals(response.getResponse().getContentAsString(),
                "Event with id null has been deleted successfully");
    }

    @Test
    public void updateEventTest() throws Exception {
        Mockito.when(eventService.updateEvent(updatedEventModel, 1L)).thenReturn(updatedEventModel);
        eventMvc.perform(MockMvcRequestBuilders.put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedEventModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
