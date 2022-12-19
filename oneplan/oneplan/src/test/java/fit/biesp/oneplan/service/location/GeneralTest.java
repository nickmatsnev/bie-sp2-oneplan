package fit.biesp.oneplan.service.location;

import fit.biesp.oneplan.exception.LocationIsMissingException;
import fit.biesp.oneplan.model.LocationModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest {

    @Test
    public void createLocationTest() {
        locationRepository.save(this.location);
        Mockito.verify(locationRepository).save(this.location);
        assertThat(this.location.getName()).isNotNull();
    }

    @Test
    public void deleteLocationTest() throws LocationIsMissingException {
        Mockito.when(locationRepository.findById(location.getId())).thenReturn(Optional.of(location));

        locationService.deleteLocation(location.getId());
        Mockito.verify(locationRepository).deleteById(location.getId());
    }

    @Test
    public void getLocationTest() {
        var savedLocation = locationRepository.save(location);
        if(savedLocation.equals(location))
            Mockito.when(LocationModel.fromModel(LocationModel.toModel(location))).thenReturn(savedLocation);

        var getResult = LocationModel.fromModel(LocationModel.toModel(location));
        assertEquals(getResult.getName(), savedLocation.getName());
    }
}
