package fit.biesp.oneplan.controller.person;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest{

    @Test
    public void createPersonTest() throws Exception {
        Mockito.when(personService.addPerson(personModel))
                .thenReturn(personModel);

        personMvc.perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(personModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getPersonTest() throws Exception {
        Mockito.when(personService.getPerson(1L))
                .thenReturn(personModel);

        personMvc.perform(MockMvcRequestBuilders.get("/persons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(personModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(personModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(personModel.getEmail())));
    }

    @Test
    public void deletePersonTest() throws Exception {
        var response = personMvc.perform(MockMvcRequestBuilders
                        .delete("/persons/{id}", 1L))
                .andReturn();

        assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    public void updatePersonTest() throws Exception {
        Mockito.when(personService.updatePerson(updatedPersonModel, 1L)).thenReturn(updatedPersonModel);
        personMvc.perform(MockMvcRequestBuilders.put("/persons/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedPersonModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
