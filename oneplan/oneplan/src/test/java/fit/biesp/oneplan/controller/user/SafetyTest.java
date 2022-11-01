package fit.biesp.oneplan.controller.user;

import fit.biesp.oneplan.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

public class SafetyTest extends BaseTest{

    @Test
    public void createEmptyUserTest() throws Exception {
        userMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getIncorrectUserTest() throws Exception {
        when(userService.getUser("999")).thenThrow(UserNotFoundException.class);

        userMvc.perform(MockMvcRequestBuilders.get("/users/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteIncorrectUserTest() throws Exception {
        when(userService.delete("999")).thenThrow(UserNotFoundException.class);

        userMvc.perform(MockMvcRequestBuilders
                        .delete("/users/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateIncorrectUserTest() throws Exception {
        when(userService.updateUser(updatedUserModel, "999")).thenThrow(UserNotFoundException.class);

        userMvc.perform(MockMvcRequestBuilders.put("/users/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
