package fit.biesp.oneplan.controller.person;

import fit.biesp.oneplan.exception.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

public class SafetyTest extends BaseTest{

    @Test
    public void createEmptyPersonTest() throws Exception {
        personMvc.perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getIncorrectPersonTest() throws Exception {
        when(personService.getPerson(999L)).thenThrow(PersonNotFoundException.class);

        personMvc.perform(MockMvcRequestBuilders.get("/persons/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteIncorrectPersonTest() throws Exception {
        when(personService.delete(999L)).thenThrow(PersonNotFoundException.class);

        personMvc.perform(MockMvcRequestBuilders
                        .delete("/persons/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateIncorrectPersonTest() throws Exception {
        when(personService.updatePerson(updatedPersonModel, 999L)).thenThrow(PersonNotFoundException.class);

        personMvc.perform(MockMvcRequestBuilders.put("/persons/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
