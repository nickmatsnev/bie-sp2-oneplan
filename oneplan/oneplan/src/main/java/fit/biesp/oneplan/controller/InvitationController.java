package fit.biesp.oneplan.controller;


import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.entity.InvitationEntity;
import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.InvitationCreateDTO;
import fit.biesp.oneplan.model.InvitationDTO;
import fit.biesp.oneplan.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    private final UserService userService;

    private final FriendService friendService;

    private final PersonService personService;



    private final String serverUrl = "https://app-oneplan-221011202557.azurewebsites.net/";
    private final String clientUrl = "https://app-client-221011202557.azurewebsites.net/";

    public InvitationController(InvitationService invitationService,
                                UserService userService,
                                FriendService friendService,
                                PersonService  personService) {
        this.invitationService = invitationService;
        this.userService = userService;
        this.friendService = friendService;
        this.personService = personService;
    }

    // data should be passed as following
    //{
    // "sender_id":N,
    // "recipient_email:"email@mail.com"
    //}
    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody InvitationCreateDTO invitationCreateDTO) throws IOException {
        // find user by email
        PersonEntity recipientEntity = personService.getByEmail(invitationCreateDTO.getRecipient_email());
        if (recipientEntity == null){
            InvitationEntity invitationEntity = new InvitationEntity(invitationCreateDTO.getSender_id(),
                    invitationCreateDTO.getRecipient_email());
            String link = clientUrl + "sentemail/" + invitationEntity.getInvitationId();
            MailService.sendEmail(invitationCreateDTO.getRecipient_email(), link);
            invitationService.create(invitationEntity);
        }else{
            InvitationEntity invitationEntity = new InvitationEntity(invitationCreateDTO.getSender_id(),
                    recipientEntity.getId().intValue(),
                    0,
                    invitationCreateDTO.getRecipient_email());
            String link = clientUrl + "sentemail/" + invitationEntity.getInvitationId();
            MailService.sendEmail(invitationCreateDTO.getRecipient_email(), link);
            invitationService.create(invitationEntity);
        }
        return new ResponseEntity<>(
                "\"" + invitationCreateDTO.getRecipient_email() + "\"",
                HttpStatus.OK
        );
        // TODO invitation list for every user
    }
    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getUserInvitations(@RequestHeader("Authorization") String header) {
        Integer userId =  Integer.valueOf(header);
        List<InvitationDTO> result = new ArrayList<>();
        List<InvitationEntity> invitationEntityList = invitationService.findAllByUserId(userId);
        for( InvitationEntity element : invitationEntityList){
            result.add( new InvitationDTO(element.getUserId(),element.getReceiverId(),element.getInvitationId()));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable("id") int invitationId, @RequestHeader("Authorization") String header) {
        Integer userId =  Integer.valueOf(header);

        if (invitationService.findByInvitationId(invitationId).getReceiverId() == userId) {
            invitationService.updateStatus(2, invitationService.findByInvitationId(invitationId));
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
    // in the passed header we have an id of the invited user
    @PostMapping("/{id}/accept")
    public ResponseEntity<String> accept(@PathVariable("id") int invitationId, @RequestHeader("Authorization") String header) throws IOException {
        Integer userId =  Integer.valueOf(header);
        UserEntity user = userService.findbyId(userId);
        InvitationEntity invitationEntity = invitationService.findByInvitationId(invitationId);
        if(invitationEntity.getReceiverId() != userId){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        FriendEntity newFriend = new FriendEntity();
        newFriend.setEmail(user.getEmail());
        newFriend.setUserId(userId);
        newFriend.setNickname(user.getNickname());
        friendService.create(newFriend);
        invitationService.updateStatus(1, invitationEntity);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
