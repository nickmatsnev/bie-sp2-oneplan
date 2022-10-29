package fit.biesp.oneplan.controller.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.controller.EventController;
import fit.biesp.oneplan.controller.LocationController;
import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;


@WebMvcTest(EventController.class)
public class BaseTest {
    @Autowired
    protected MockMvc eventMvc;
    @MockBean
    protected EventService eventService;

    protected ObjectMapper objectMapper;
    protected EventEntity eventEntity;
    protected EventModel eventModel;

    protected EventEntity updatedEventEntity;
    protected EventModel updatedEventModel;

    @BeforeEach
    public void initialise() {
        this.eventEntity = new EventEntity(
                1L,
                new LocationEntity(),
                new UserEntity(),
                Collections.emptyList(),
                "name",
                "description",
                Date.valueOf("2022-02-30"),
                Time.valueOf("11:11:00"),
                10);
        this.updatedEventEntity = new EventEntity(
                1L,
                new LocationEntity(),
                new UserEntity(),
                Collections.emptyList(),
                "name",
                "description",
                Date.valueOf("2022-05-30"),
                Time.valueOf("11:12:00"),
                10);
        this.updatedEventModel = EventModel.toModel(updatedEventEntity);
        this.eventModel = EventModel.toModel(eventEntity);
        this.objectMapper = new ObjectMapper();
    }

}
