package fit.biesp.oneplan.service.location;

import fit.biesp.oneplan.exception.LocationIsMissingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class SafetyTest extends BaseTest {

    @Test
    public void createInvalidLocationTest() {
        locationRepository.save(null);
        Mockito.verify(locationRepository).save(null);
    }

    @Test
    public void deleteInvalidLocationTest() throws LocationIsMissingException {
        Assertions.assertThrows(LocationIsMissingException.class,
                () -> locationService.deleteLocation(location.getId()));
    }

    @Test
    public void getMissingLocationTest() {
        Assertions.assertThrows(LocationIsMissingException.class,
                () -> locationService.getLocation(location.getId()));
    }
}
