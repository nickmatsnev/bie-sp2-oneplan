package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.FriendEntity;
import fit.biesp.oneplan.entity.InvitationEntity;
import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.model.FriendCreateModel;
import fit.biesp.oneplan.model.FriendFullModel;
import fit.biesp.oneplan.model.FriendModel;
import fit.biesp.oneplan.model.InvitationWithNameDTO;
import fit.biesp.oneplan.service.FriendService;
import fit.biesp.oneplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController{

    private FriendService friendService;

    private final UserService userService;

    public FriendController(FriendService friendService, UserService userService) {
        this.friendService = friendService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity addFriend(FriendModel friendModel){
        try{
            friendService.addFriend(friendModel);
            return ResponseEntity.ok("Friend created!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }
    @PostMapping("/create")
    public ResponseEntity addFriendNew(@RequestBody FriendCreateModel friendModel){
        try{
            System.out.println("friends email: "  + friendModel.getEmail());
            friendService.addFriendNew(friendModel);
            return ResponseEntity.ok("Friend created!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getFriend(@PathVariable("id") String nickname){
        try{
            return ResponseEntity.ok(friendService.getFriend(nickname));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFriend(@RequestBody FriendModel friendModel, @PathVariable("id") String nickname) {
        try {
            return ResponseEntity.ok(friendService.updateFriend(friendModel, nickname));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @PostMapping("/{userid}/{email}")
    public  ResponseEntity deleteFriend(@PathVariable("userid") int userId, @PathVariable("email") String recipientEmail){
        try {
            System.out.println("Welcome in delete friend");
            System.out.println(recipientEmail);
            System.out.println(userId);
            return ResponseEntity.ok(friendService.deleteByUserIdAndEmail(recipientEmail, userId));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/user/{nickname}")
    public ResponseEntity<List<FriendFullModel>> getFriendsByNickname(@PathVariable("nickname") String nickname){
        UserEntity user = userService.findByNickname(nickname);
        List<FriendFullModel> result = new ArrayList<>();
        List<FriendEntity> friendEntityList = friendService.findAllByUserId(Math.toIntExact(user.getId()));
        for( FriendEntity element : friendEntityList){
            result.add( new FriendFullModel(
                    (int) element.getId(),
                    element.getUserId(),
                    element.getEmail(),
                    element.getNickname()
                    ));
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
