package fit.biesp.oneplan.controller.friend;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

public class SafetyTest extends BaseTest{

    @Test
    public void createEmptyFriendTest() throws Exception {
        friendMvc.perform(MockMvcRequestBuilders.post("/friends")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void getIncorrectFriendTest() throws Exception {
        when(friendService.getFriend("999")).thenThrow(Exception.class);

        friendMvc.perform(MockMvcRequestBuilders.get("/friends/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deleteIncorrectFriendTest() throws Exception {
        when(friendService.delete("999")).thenThrow(Exception.class);

        friendMvc.perform(MockMvcRequestBuilders
                        .delete("/friends/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void updateIncorrectFriendTest() throws Exception {
        when(friendService.updateFriend(updatedFriendModel, "999")).thenThrow(Exception.class);

        friendMvc.perform(MockMvcRequestBuilders.put("/friends/999"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
