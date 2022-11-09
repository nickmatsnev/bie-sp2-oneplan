package fit.biesp.oneplan.controller.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest {

    @Test
    public void createUserTest() throws Exception {
        Mockito.when(userService.registration(userRegistrationModel))
                .thenReturn(userModel);

        userMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRegistrationModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getUserTest() throws Exception {
        Mockito.when(userService.getUser("name"))
                .thenReturn(userModel);

        userMvc.perform(MockMvcRequestBuilders.get("/users/{id}", "name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(userModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname", is(userModel.getNickname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(userModel.getEmail())));
    }

    @Test
    public void deleteUserTest() throws Exception {
        var response = userMvc.perform(MockMvcRequestBuilders
                        .delete("/users/{id}", "name"))
                .andReturn();

        assertEquals(response.getResponse().getStatus(), 200);
    }

    @Test
    public void updateUserTest() throws Exception {
        Mockito.when(userService.updateUser(updatedUserModel, "name")).thenReturn(updatedUserModel);
        userMvc.perform(MockMvcRequestBuilders.put("/users/name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedUserModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
