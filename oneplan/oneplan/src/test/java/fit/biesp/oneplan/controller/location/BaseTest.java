package fit.biesp.oneplan.controller.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.controller.LocationController;
import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.model.LocationModel;
import fit.biesp.oneplan.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(LocationController.class)
public class BaseTest {
    @Autowired
    protected MockMvc locationMvc;
    @MockBean
    protected LocationService locationService;

    protected LocationEntity locationEntity;
    protected LocationModel locationModel;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void initialise() {
        this.locationEntity = new LocationEntity(
                1L,
                "name",
                "address",
                "coords",
                Collections.<EventEntity>emptyList());

        this.locationModel = LocationModel.toModel(locationEntity);
        this.objectMapper = new ObjectMapper();
    }

}
