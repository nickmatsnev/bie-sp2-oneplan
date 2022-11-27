package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.model.EventInviteModel;
import fit.biesp.oneplan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    // TODO create
    @PostMapping("/create")
    public ResponseEntity createInviteToEvent(@RequestBody EventInviteModel inviteModel){
        try {
            System.out.println(inviteModel.getRecipientEmail());
            var message = eventInvitationService.create(inviteModel);
            return ResponseEntity.ok("" + message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Event invite creation error. " + e.getMessage());
        }
    }
    // TODO getOne
    // TODO getAllBySender
    // TODO getAllByEvent
    // TODO update
}
