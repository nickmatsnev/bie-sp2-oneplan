package fit.biesp.oneplan.controller.eventinvites;

import fit.biesp.oneplan.controller.EventInvitationsController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.when;


public class GeneralTest extends BaseTest {
    @Test
    @DisplayName("create invite to event test")
    public void createInviteEventTest() throws Exception {
        when(eventInvitationsService.create(invitationsEntity))
                .thenReturn("Invitation to event successfully created");

        mockMvc.perform(MockMvcRequestBuilders.post("/event-invites/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventModel)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    @DisplayName("get invite to event test by email")
    public void getEventTest() throws Exception {
        when(eventInvitationsService.getByEmailAndSenderId( userEntity.getEmail(), userEntity))
                .thenReturn(invitationsEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/event-invites/{email}", userEntity.getEmail())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventInviteModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(eventModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(eventModel.getName())));
    }

    @Test
    @DisplayName("get invite to event test by nickname")
    public void getEventTestNickname() throws Exception {
        when(eventInvitationsService.getByEmailAndSenderId( userEntity.getEmail(), userEntity))
                .thenReturn(invitationsEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/event-invites/nickname/{nickname}", userEntity.getNickname())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventInviteModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(eventModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(eventModel.getName())));
    }
    @Test
    @DisplayName("get pending invites to event test by nickname")
    public void getPendingInvitesSenderNickname() throws Exception {
        when(eventInvitationsService.getEntitiesByEmail( userEntity.getEmail()))
                .thenReturn(entities);

        mockMvc.perform(MockMvcRequestBuilders.get("/event-invites/invites/{nickname}/pending", invitationsEntity.getSenderId().getNickname())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventInviteModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(eventModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(eventModel.getName())));
    }
    @Test
    @DisplayName("get rejected invites to event test by nickname")
    public void getRejectedInvitesSenderNickname() throws Exception {
        when(eventInvitationsService.getEntitiesByEmail( userEntity.getEmail()))
                .thenReturn(entities);

        mockMvc.perform(MockMvcRequestBuilders.get("/event-invites/invites/{nickname}/rejected", invitationsEntity.getSenderId().getNickname())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventInviteModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(eventModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(eventModel.getName())));
    }

    @Test
    @DisplayName("get accepted invites to event test by nickname")
    public void getAcceptedInvitesSenderNickname() throws Exception {
        when(eventInvitationsService.getEntitiesByEmail( userEntity.getEmail()))
                .thenReturn(entities);

        mockMvc.perform(MockMvcRequestBuilders.get("/event-invites/invites/{nickname}/accepted", invitationsEntity.getSenderId().getNickname())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventInviteModel)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", is(eventModel.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is(eventModel.getName())));
    }
}
