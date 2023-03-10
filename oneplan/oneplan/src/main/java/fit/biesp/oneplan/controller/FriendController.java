package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.model.FriendModel;
import fit.biesp.oneplan.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

//    private final UserService userService;
//
//    public FriendController(FriendService friendService, UserService userService) {
//        this.friendService = friendService;
//        this.userService = userService;
//    }

    @PostMapping()
    public ResponseEntity addFriend(FriendModel friendModel) {
        try {
            friendService.addFriend(friendModel);
            return ResponseEntity.ok("Friend added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getFriend(@PathVariable("id") String nickname) {
        try {
            return ResponseEntity.ok(friendService.getFriend(nickname));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteFriend(@PathVariable("id") String nickname) {
        try {
            return ResponseEntity.ok(friendService.delete(nickname));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
//
//    @GetMapping("/user/{nickname}")
//    public ResponseEntity<List<FriendFullModel>> getFriendsByNickname(@PathVariable("nickname") String nickname){
//        UserEntity user = userService.findByNickname(nickname);
//        List<FriendFullModel> result = new ArrayList<>();
//        List<FriendEntity> friendEntityList = friendService.findAllByUserId(Math.toIntExact(user.getId()));
//        for( FriendEntity element : friendEntityList){
//            result.add( new FriendFullModel(
//                    (int) element.getId(),
//                    element.getUserId(),
//                    element.getEmail(),
//                    element.getNickname()
//                    ));
//        }
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
