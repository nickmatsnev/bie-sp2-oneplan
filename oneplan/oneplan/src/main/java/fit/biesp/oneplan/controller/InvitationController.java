package fit.biesp.oneplan.controller;


import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.entity.InvitationEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.InvitationDTO;
import fit.biesp.oneplan.service.FriendService;
import fit.biesp.oneplan.service.InvitationService;
import fit.biesp.oneplan.service.MailService;
import fit.biesp.oneplan.service.UserService;
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


    private final String serverUrl = "https://app-oneplan-221011202557.azurewebsites.net/";

    public InvitationController(InvitationService invitationService, UserService userService, FriendService friendService) {
        this.invitationService = invitationService;
        this.userService = userService;
        this.friendService = friendService;
    }

    @PostMapping("/send")
    public void send(@RequestBody String email){
        // find user by email
        // if exists then create invitation with receiver id and offer to login
        // else create invitation without receiver id and offer to register
        // ALSO change the void retturn type to ResponceEntity<String> or whatevs
        // TODO
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
            invitationService.delete(invitationId);
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
        MailService.sendEmail(user.getEmail(), serverUrl + "/" + invitationId + "/accept");
        FriendEntity newFriend = new FriendEntity();
        newFriend.setEmail(user.getEmail());
        newFriend.setUserId(userId);
        newFriend.setNickname(user.getNickname());
        friendService.create(newFriend);
        // TODO update of status instead of delete
        invitationService.delete(invitationId);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
