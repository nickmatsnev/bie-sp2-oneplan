package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.*;
import fit.biesp.oneplan.model.*;
import fit.biesp.oneplan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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


    private final String clientUrl;

    public EventInvitationsController(@Value("${client.url}") String clientUrl) {
        this.clientUrl = clientUrl;
    }

    @PostMapping("/create")
    public ResponseEntity createInviteToEvent(@RequestBody EventInviteRealModel inviteModel){
        try {
            System.out.println(inviteModel.getRecipientEmail());
            EventInvitationsEntity entity = new EventInvitationsEntity();
            entity.setRecipientEmail(inviteModel.getRecipientEmail());
            entity.setEventId(EventModel.fromModel(eventService.getEvent((long) inviteModel.getEventModelId())));
            entity.setSenderId(userService.findbyId((long) inviteModel.getSenderId()));
            entity.setStatus(0);
            String link = clientUrl + "sendemail/" + inviteModel.getSenderId() + "/" + inviteModel.getRecipientEmail();
            MailService.sendAcceptReject(inviteModel.getRecipientEmail(), entity, clientUrl);
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
    @GetMapping("/sender/nickname/{nickname}")
    public ResponseEntity getInvitesBySenderNickname(@PathVariable("nickname") String nickname){
        try{
            UserEntity user = userService.findByNickname(nickname);
            List<EventInvitationsEntity> entities = eventInvitationService.getEntitiesBySender(user);
            List<EventInviteModel> models = new ArrayList<>();
            for(EventInvitationsEntity e: entities){
                models.add(EventInviteModel.toModel(e));
            }
            return ResponseEntity.ok(models);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite parsing error. " + e.getMessage());
        }
    }
    @GetMapping("/invites/{nickname}/pending")
    public ResponseEntity getPendingInvitesByRecipientNickName(@PathVariable("nickname") String nickname){
        try{
            UserEntity user = userService.findByNickname(nickname);
            List<EventInvitationsEntity> entities = eventInvitationService.getEntitiesByEmail(user.getEmail());
            List<EventInviteModel> models = new ArrayList<>();
            for(EventInvitationsEntity e: entities){
                if (e.getStatus() == 0) {
                    models.add(EventInviteModel.toModel(e));
                }
            }
            return ResponseEntity.ok(models);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    @GetMapping("/invites/{nickname}/accepted")
    public ResponseEntity getAcceptedInvitesByRecipientNickName(@PathVariable("nickname") String nickname){
        try{
            UserEntity user = userService.findByNickname(nickname);
            List<EventInvitationsEntity> entities = eventInvitationService.getEntitiesByEmail(user.getEmail());
            List<EventInviteModel> models = new ArrayList<>();
            for(EventInvitationsEntity e: entities){
                if (e.getStatus() == 1) {
                    models.add(EventInviteModel.toModel(e));
                }
            }
            return ResponseEntity.ok(models);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    @GetMapping("/invites/{nickname}/rejected")
    public ResponseEntity getRejectedInvitesByRecipientNickName(@PathVariable("nickname") String nickname){
        try{
            UserEntity user = userService.findByNickname(nickname);
            List<EventInvitationsEntity> entities = eventInvitationService.getEntitiesByEmail(user.getEmail());
            List<EventInviteModel> models = new ArrayList<>();
            for(EventInvitationsEntity e: entities){
                if (e.getStatus() == 2){
                    models.add(EventInviteModel.toModel(e));
                }
            }
            return ResponseEntity.ok(models);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    @GetMapping("/accept/{email}/{senderid}")
    public ResponseEntity accept(@PathVariable("email") String email, @PathVariable("senderid") long senderId){
        try{
            UserEntity user = userService.findbyId(senderId);

            EventInvitationsEntity entity = eventInvitationService.getByEmailAndSenderId(email, user);
            EventEntity event = entity.getEventId();
            event.addAttendee(user);


            UserEntity optRecipient = entity.getSenderId();

            List<EventEntity> optRecipientList = optRecipient.getEvents();

            optRecipientList.add(event);
            optRecipient.setEvents(optRecipientList);

            userService.updateUserEntity(optRecipient, optRecipient.getNickname());
            entity.setStatus(1);
            return ResponseEntity.ok(eventInvitationService.acceptByRecipientEmailAndSenderId(email, user));
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }
    @GetMapping("/reject/{email}/{senderid}")
    public ResponseEntity reject(@PathVariable("email") String email, @PathVariable("senderid") long senderId){
        try{
            UserEntity user = userService.findbyId(senderId);
            return ResponseEntity.ok(eventInvitationService.rejectByRecipientEmailAndSenderId(email, user));
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }
    @GetMapping("/delete/{email}/{senderid}")
    public ResponseEntity delete(@PathVariable("email") String email, @PathVariable("senderid") int senderId){
        try{
            UserEntity user = userService.findbyId((long) senderId);
            return ResponseEntity.ok(eventInvitationService.deleteByRecipientEmailAndSenderId(email, user));
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }
    @GetMapping("/delete-event-invites/{eventid}")
    public ResponseEntity deleteAllByEventId(@PathVariable("eventid") long eventId){
        try{
            List<EventInvitationsEntity> entities = eventInvitationService.getAllByEvent(eventService.getEventEntity(eventId));
            for(var i : entities){
                eventInvitationService.delete(i);
            }
            return ResponseEntity.ok("all purged.");
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }

    @GetMapping("/get-invited-to-event/{eventid}")
    public ResponseEntity getInvitedToEvent(@PathVariable("eventid") long eventId){
        try{
            List<EventInvitationsEntity> invitesToEvent = eventInvitationService.getAllByEvent(eventService.getEventEntity(eventId));
            List<FriendModel> friendsInvited = new ArrayList<>();
            List<UserModel> usersInvited = new ArrayList<>();
            for(EventInvitationsEntity i : invitesToEvent){
                if(i.getStatus() == 0){
                    System.out.println(i.getStatus());
                    if(userService.findByEmail(i.getRecipientEmail()).getId() == -2){
                        friendsInvited.add(friendService.findFriendByEmailAndUserId(i.getRecipientEmail(), i.getSenderId().getId()));
                    }else{
                        usersInvited.add(UserModel.toModel(userService.findByEmail(i.getRecipientEmail())));
                    }
                }
            }
            InvitedToEventModel invited = new InvitedToEventModel(usersInvited, friendsInvited);
            return ResponseEntity.ok(invited);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }
    @GetMapping("/get-accepted-to-event/{eventid}")
    public ResponseEntity getAcceptedToEvent(@PathVariable("eventid") long eventId){
        try{
            List<EventInvitationsEntity> invitesToEvent = eventInvitationService.getAllByEvent(eventService.getEventEntity(eventId));
            List<FriendModel> friendsInvited = new ArrayList<>();
            List<UserModel> usersInvited = new ArrayList<>();
            for(EventInvitationsEntity i : invitesToEvent){
                if(i.getStatus() == 1){
                    System.out.println(i.getStatus());
                    if(userService.findByEmail(i.getRecipientEmail()).getId() == -2){
                    friendsInvited.add(friendService.findFriendByEmailAndUserId(i.getRecipientEmail(), i.getSenderId().getId()));
                    }else{
                        usersInvited.add(UserModel.toModel(userService.findByEmail(i.getRecipientEmail())));
                    }
                }

            }
            InvitedToEventModel invited = new InvitedToEventModel(usersInvited, friendsInvited);
            return ResponseEntity.ok(invited);
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Event invite acceptance error. " + e.getMessage());
        }
    }

}
