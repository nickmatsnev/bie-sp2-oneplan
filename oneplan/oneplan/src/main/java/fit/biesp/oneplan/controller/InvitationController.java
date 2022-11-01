package fit.biesp.oneplan.controller;


import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.entity.InvitationEntity;
import fit.biesp.oneplan.entity.PersonEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.InvitationCreateDTO;
import fit.biesp.oneplan.model.InvitationDTO;
import fit.biesp.oneplan.model.InvitationWithNameDTO;
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
        PersonEntity recipientEntity = personService.unsafeGetByEmail(invitationCreateDTO.getRecipient_email());

        if (recipientEntity == null){
            InvitationEntity invitationEntity = new InvitationEntity(Long.valueOf(invitationCreateDTO.getSender_id()),
                    invitationCreateDTO.getRecipient_email());

            String link = clientUrl + "sendemail/" + invitationEntity.getInvitationId();
            MailService.sendEmail(invitationCreateDTO.getRecipient_email(), link);
            invitationService.create(invitationEntity);
        }else{
            InvitationEntity invitationEntity = new InvitationEntity(Long.valueOf(invitationCreateDTO.getSender_id()),
                    0,
                    invitationCreateDTO.getRecipient_email());
            String link = clientUrl + "sendemail/" + invitationEntity.getInvitationId();
            MailService.sendEmail(invitationCreateDTO.getRecipient_email(), link);
            invitationService.create(invitationEntity);
        }
        return new ResponseEntity<>(
                "\"" + invitationCreateDTO.getRecipient_email() + "\"",
                HttpStatus.OK
        );
    }
    @GetMapping("user/{id}")
    public ResponseEntity<List<InvitationDTO>> getUserInvitations(@PathVariable("id") Integer id) {
        List<InvitationDTO> result = new ArrayList<>();
        System.out.println(id);
        List<InvitationEntity> invitationEntityList = invitationService.findAllByUserId(id);
        for( InvitationEntity element : invitationEntityList){
            result.add( new InvitationDTO(element.getUserId().intValue(), element.getReceiverEmail(), element.getInvitationId()));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/user/nickname/{nickname}")
    public ResponseEntity<List<InvitationDTO>> getByNickname(@PathVariable("nickname") String nickname){
        UserEntity user = userService.findByNickname(nickname);
        List<InvitationDTO> result = new ArrayList<>();
        List<InvitationEntity> invitationEntityList = invitationService.findAllByUserId(Math.toIntExact(user.getId()));
        for( InvitationEntity element : invitationEntityList){
            result.add( new InvitationDTO(element.getUserId().intValue(), element.getReceiverEmail(), element.getInvitationId()));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InvitationWithNameDTO> getInvitation(@PathVariable("id") Integer id) {
        InvitationEntity invitationEntity = invitationService.findByInvitationId(id);
        InvitationWithNameDTO invitationWithNameDTO = new InvitationWithNameDTO(
                userService.findbyId(invitationEntity.getUserId()).getNickname(),
                invitationEntity.getReceiverEmail(),
                invitationEntity.getInvitationId()
        );
        return new ResponseEntity<>(invitationWithNameDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity getAllInvitations() {
        try {
            // so what I aim at here is
            // sending entities to convert the values to hash on the server side and
            // whenever someone calls client/invitations/hashcodeOfInvite,
            // client service will check whether hash is valid and then
            // would show options according to the status of the invitation
            return ResponseEntity.ok(invitationService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while trying to list locations. " + e.getMessage());
        }
    }



    @PostMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable("id") Integer invitationId) {

        if (invitationService.findByInvitationId(invitationId) != null) {
            invitationService.updateStatus(2, invitationService.findByInvitationId(invitationId));
            return new ResponseEntity<>("2", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
    // in the passed header we have an id of the invited user
    @PostMapping("/{id}/accept")
    public ResponseEntity<String> accept(@PathVariable("id") Integer invitationId) {
        InvitationEntity invitationEntity = invitationService.findByInvitationId(invitationId);
        if(invitationEntity == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        FriendEntity newFriend = new FriendEntity();

        newFriend.setEmail(invitationEntity.getReceiverEmail());
        UserEntity user = userService.findbyId(invitationEntity.getUserId());
        newFriend.setUserId(invitationEntity.getUserId().intValue());
        newFriend.setNickname(user.getNickname());
        System.out.println(newFriend.getEmail());
        System.out.println(newFriend.getNickname());
        System.out.println(newFriend.getUserId());
        friendService.create(newFriend);
        invitationService.updateStatus(1, invitationEntity);
        return new ResponseEntity<>("1", HttpStatus.OK);
    }
}
