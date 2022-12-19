package fit.biesp.oneplan.service.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.repository.LocationRepository;
import fit.biesp.oneplan.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;


@SpringBootTest
public class BaseTest {
    @Autowired
    protected LocationService locationService;
    @MockBean
    protected LocationRepository locationRepository;

    protected LocationEntity location;
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void initialise() {
        this.location = new LocationEntity(
                1L,
                "name",
                "address",
                "coords",
                Collections.<EventEntity>emptyList());
        this.objectMapper = new ObjectMapper();
        Mockito.when(locationRepository.save(location)).thenReturn(location);
    }

}
