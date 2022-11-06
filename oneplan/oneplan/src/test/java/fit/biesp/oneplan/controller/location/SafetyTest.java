package fit.biesp.oneplan.controller.location;

import fit.biesp.oneplan.exception.LocationIsMissingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

public class SafetyTest extends BaseTest {

    @Test
    public void createEmptyLocationTest() throws Exception {
        locationMvc.perform(MockMvcRequestBuilders.post("/locations")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getIncorrectLocationTest() throws Exception {
        when(locationService.getLocation(999L)).thenThrow(LocationIsMissingException.class);

        locationMvc.perform(MockMvcRequestBuilders.get("/locations/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteIncorrectLocationTest() throws Exception {
        when(locationService.deleteLocation(999L)).thenThrow(LocationIsMissingException.class);

        locationMvc.perform(MockMvcRequestBuilders
                        .delete("/locations/999", 999L))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
