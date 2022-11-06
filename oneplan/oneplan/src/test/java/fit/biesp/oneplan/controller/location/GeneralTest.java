package fit.biesp.oneplan.controller.location;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest {

    @Test
    public void createLocationTest() throws Exception {
        Mockito.when(locationService.postLocation(locationModel))
                .thenReturn(locationModel);

        locationMvc.perform(MockMvcRequestBuilders.post("/locations")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(locationModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getLocationTest() throws Exception {
        Mockito.when(locationService.getLocation(1L))
                .thenReturn(locationModel);

        locationMvc.perform(MockMvcRequestBuilders.get("/locations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(locationModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(locationModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(locationModel.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", is(locationModel.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinates", is(locationModel.getCoordinates())));
    }

    @Test
    public void deleteLocationTest() throws Exception {
        var response = locationMvc.perform(MockMvcRequestBuilders
                        .delete("/locations/{id}", 1L))
                .andReturn();

        assertEquals(response.getResponse().getStatus(), 200);
        assertEquals(response.getResponse().getContentAsString(),
                "Location successfully deleted");
    }

}
