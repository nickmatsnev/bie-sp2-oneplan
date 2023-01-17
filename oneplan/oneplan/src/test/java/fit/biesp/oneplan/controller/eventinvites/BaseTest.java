package fit.biesp.oneplan.controller.eventinvites;

import com.fasterxml.jackson.databind.ObjectMapper;
import fit.biesp.oneplan.controller.EventInvitationsController;
import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.EventInvitationsEntity;
import fit.biesp.oneplan.entity.LocationEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.*;
import fit.biesp.oneplan.service.EventInvitationsService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebMvcTest(EventInvitationsController.class)
public class BaseTest {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected EventInvitationsService eventInvitationsService;
    protected ObjectMapper objectMapper;
    protected EventInvitationsEntity invitationsEntity;
    protected EventInviteModel eventInviteModel;
    protected EventInviteRealModel eventInviteRealModel;
    protected UserEntity userEntity;
    protected EventEntity eventEntity;
    protected UserModel userModel;
    protected EventModel eventModel;
    protected UserRegistrationModel userRegistrationModel;
    protected List<EventInvitationsEntity> entities = new ArrayList<>();
    @BeforeEach
    public void initialise(){
        this.eventEntity = new EventEntity(
                1L,
                new LocationEntity(),
                new UserEntity(),
                Collections.emptyList(),
                "name",
                "description",
                Date.valueOf("2022-02-30"),
                Time.valueOf("11:11:00"),
                10);
        this.userEntity = new UserEntity(
                "name", "email", "password");

        this.userModel = UserModel.toModel(userEntity);
        userModel.setId(1L);
        this.userRegistrationModel = UserRegistrationModel.toModel(userEntity);
        this.eventModel = EventModel.toModel(eventEntity);
        this.objectMapper = new ObjectMapper();
        this.invitationsEntity = new EventInvitationsEntity(this.userEntity, "email", this.eventEntity);
        this.eventInviteModel = EventInviteModel.toModel(this.invitationsEntity);
        this.entities.add(invitationsEntity);
    }
}
