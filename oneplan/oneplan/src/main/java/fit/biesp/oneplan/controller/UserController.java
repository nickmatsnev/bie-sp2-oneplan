package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.*;
import fit.biesp.oneplan.service.MailService;
import fit.biesp.oneplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity registration(@RequestBody UserRegistrationModel userModel){
        try{
            userService.registration(userModel);
            return ResponseEntity.ok("User created!");
        } catch (UserAlreadyExistsException e){
            return ResponseEntity.ok(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel loginModel) {
        try {
            boolean hasLoggedIn = userService.loginUser(loginModel.getNickname(), loginModel.getPassword());
            if (!hasLoggedIn) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        UserEntity ent = userService.findByNickname(loginModel.getNickname());

        return new ResponseEntity<>(
                ent.getId().toString(),
                HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") String nickname){
        try{
            return ResponseEntity.ok(userService.getUser(nickname));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity getUserById(@PathVariable("id") long id){
        try{
            return ResponseEntity.ok(UserModel.toModel(userService.getById(id)));
        } catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

//    @GetMapping("/{id}/password")
//    public String getUserPassword(@PathVariable("id") String nickname){
//        try{
//            return userService.getPassword(nickname);
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }


    @PutMapping("/{id}")
    public ResponseEntity updateUser(@RequestBody UserModel userModel, @PathVariable("id") String nickname) {
        try {
            return ResponseEntity.ok(userService.updateUser(userModel, nickname));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deleteUser(@PathVariable("id") String nickname){
        try {
            return ResponseEntity.ok(userService.delete(nickname));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return  ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/{id}/events")
    public ResponseEntity getEvents(@PathVariable("id") String nickname) {
        try{
            List<EventModel> events = new ArrayList<>();
            for(var i : userService.getEvents(nickname)){
                events.add(EventModel.toModel(i));
                System.out.println(i.getName());
            }

            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserEntity> entities = userService.getAll();
        List<UserModel> models = new ArrayList<>();
        for(UserEntity entity : entities){
            models.add(new UserModel(
                    entity.getId(),
                    entity.getNickname(),
                    entity.getEmail()
            ));
        }
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @GetMapping("/verify/{email}")
    public ResponseEntity verifyEmail(@PathVariable("email") String email) throws UserNotFoundException {
        UserEntity user = userService.findByEmail(email);
        user.setStatus(1);
        userService.updateUserEntity(user, user.getNickname());
        return ResponseEntity.ok("Successfully verified!");
    }

    @PostMapping("/send-password-email/")
    public ResponseEntity sendEmailForPassword(@RequestBody PasswordRecoveryRequestModel model) throws IOException {
        String linkToInvite = "http://safe-forest-87004.herokuapp.com/newPassword/" + model.getEmail();
        MailService.verifyPasswordChange(model.getEmail(), linkToInvite);
        return ResponseEntity.ok("email for password change is sent!");
    }

    @PostMapping("/update-password/{email}")
    public ResponseEntity updatePasswordByEmail(@PathVariable("email") String email, @RequestBody UpdatePasswordModel model) throws IOException {
        UserEntity user = userService.findByEmail(email);
        user.setPassword(model.getPassword());
        return ResponseEntity.ok("email for password change is sent!");
    }

}
