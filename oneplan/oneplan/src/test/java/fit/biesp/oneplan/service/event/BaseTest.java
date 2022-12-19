package fit.biesp.oneplan.service.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.repository.EventRepository;
import fit.biesp.oneplan.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Date;
import java.sql.Time;
import java.util.Collections;


@SpringBootTest
public class BaseTest {
    @Autowired
    protected EventService eventService;
    @MockBean
    protected EventRepository eventRepository;

    protected EventEntity event;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void initialise() {
        this.event = new EventEntity(
                1L,
                new LocationEntity(),
                new UserEntity(),
                Collections.emptyList(),
                "name",
                "description",
                Date.valueOf("2022-02-30"),
                Time.valueOf("11:11:00"),
                10);
        this.objectMapper = new ObjectMapper();
        Mockito.when(eventRepository.save(event)).thenReturn(event);
    }

}
