package fit.biesp.oneplan.controller.friend;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralTest extends BaseTest{

    @Test
    public void createFriendTest() throws Exception {
        Mockito.when(friendService.addFriend(friendModel))
                .thenReturn("Friend successfully created");

        friendMvc.perform(MockMvcRequestBuilders.post("/friends")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(friendModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getFriendTest() throws Exception {
        Mockito.when(friendService.getFriend("name"))
                .thenReturn(friendModel);

        friendMvc.perform(MockMvcRequestBuilders.get("/friends/{id}", "name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(friendModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(friendModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname", is(friendModel.getNickname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", is(friendModel.getEmail())));
    }

    @Test
    public void deleteFriendTest() throws Exception {
        var response = friendMvc.perform(MockMvcRequestBuilders
                        .delete("/friends/{id}", "name"))
                .andReturn();

        assertEquals(response.getResponse().getStatus(), 200);
        assertEquals(response.getResponse().getContentAsString(),
                "Friend has been deleted successfully");
    }

    @Test
    public void updateFriendTest() throws Exception {
        Mockito.when(friendService.updateFriend(updatedFriendModel, "name")).thenReturn(updatedFriendModel);
        friendMvc.perform(MockMvcRequestBuilders.put("/friends/name")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(updatedFriendModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
