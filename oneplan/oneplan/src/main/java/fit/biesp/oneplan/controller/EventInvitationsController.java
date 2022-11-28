package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.EventEntity;
import fit.biesp.oneplan.entity.EventInvitationsEntity;
import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.EventInviteModel;
import fit.biesp.oneplan.model.EventInviteRealModel;
import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event-invites")
public class EventInvitationsController {
    @Autowired
    private EventInvitationsService eventInvitationService;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;
    @Autowired
    private PersonService personService;
    @Autowired
    private EventService eventService;


    private final String clientUrl = "http://localhost:8090/";

    @PostMapping("/create")
    public ResponseEntity createInviteToEvent(@RequestBody EventInviteRealModel inviteModel){
        try {
            System.out.println(inviteModel.getRecipientEmail());
            EventInvitationsEntity entity = new EventInvitationsEntity();
            entity.setRecipientEmail(inviteModel.getRecipientEmail());
            entity.setEventId(EventModel.fromModel(eventService.getEvent((long) inviteModel.getEventModelId())));
            entity.setSenderId(userService.findbyId((long) inviteModel.getSenderId()));
            entity.setStatus(0);
            var message = eventInvitationService.create(entity);
            return ResponseEntity.ok("" + message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    @GetMapping("/{email}")
    public ResponseEntity getInvitesByRecipientEmail(@PathVariable("email") String email){
        try{
            List<EventInvitationsEntity> entities = eventInvitationService.getEntitiesByEmail(email);
            List<EventInviteModel> models = new ArrayList<>();
            for(EventInvitationsEntity e: entities){
                models.add(EventInviteModel.toModel(e));
            }
            return ResponseEntity.ok(models);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity getInvitesByRecipientNickName(@PathVariable("nickname") String nickname){
        try{
            UserEntity user = userService.findByNickname(nickname);
            List<EventInvitationsEntity> entities = eventInvitationService.getEntitiesByEmail(user.getEmail());
            List<EventInviteModel> models = new ArrayList<>();
            for(EventInvitationsEntity e: entities){
                models.add(EventInviteModel.toModel(e));
            }
            return ResponseEntity.ok(models);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    @GetMapping("/accept/{email}/{senderid}")
    public ResponseEntity accept(@PathVariable("email") String email, @PathVariable("senderid") int senderId){
        try{
            UserEntity user = userService.findbyId((long) senderId);
            EventInvitationsEntity entity = eventInvitationService.getByEmailAndSenderId(email, user);
            EventEntity event = entity.getEventId();

            List<PersonEntity> attendees = event.getAttendees();

            UserEntity optRecipient = entity.getSenderId();

            System.out.println(optRecipient.getNickname());
            if(attendees == null){
                List<PersonEntity> newAttendees = new ArrayList<>();
                newAttendees.add(optRecipient);
                event.setAttendees(newAttendees);
            }else{
                attendees.add(optRecipient);
                event.setAttendees(attendees);
            }
            System.out.println(optRecipient.getNickname());
            eventService.updateEvent(EventModel.toModel(event), event.getId());

            List<EventEntity> optRecipientList = optRecipient.getEvents();
            optRecipientList.add(event);
            optRecipient.setEvents(optRecipientList);
            userService.updateUser(UserModel.toModel(optRecipient), optRecipient.getNickname());
            entity.setStatus(1);
            return ResponseEntity.ok(eventInvitationService.acceptByRecipientEmailAndSenderId(email, user));
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }
    @GetMapping("/reject/{email}/{senderid}")
    public ResponseEntity reject(@PathVariable("email") String email, @PathVariable("senderid") int senderId){
        try{
            UserEntity user = userService.findbyId((long) senderId);
            return ResponseEntity.ok(eventInvitationService.rejectByRecipientEmailAndSenderId(email, user));
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }
    // TODO getOne
    // TODO getAllBySender
    // TODO getAllByEvent
    // TODO update
}
