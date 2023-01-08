package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.entity.UserEntity;
import fit.biesp.oneplan.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.*;
import fit.biesp.oneplan.service.MailService;
import fit.biesp.oneplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    private final String clientUrl;

    public UserController(@Value("${client.url}") String clientUrl) {
        this.clientUrl = clientUrl;
    }

    private String getRandomStringOfSize(int size){
        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

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

    @PostMapping("/verify/{email}")
    public ResponseEntity verifyEmail(@PathVariable("email") String email, @RequestBody PasswordRecoveryRequestModel model) throws UserNotFoundException {
        UserEntity user = userService.findByEmail(email);
        if (!Objects.equals(user.getSecret(), model.getEmail())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        user.setStatus(1);
        userService.updateUserEntity(user, user.getNickname());
        return ResponseEntity.ok("Successfully verified!");
    }

    @GetMapping("/send-password-email/{email}")
    public ResponseEntity sendEmailForPassword(@PathVariable("email") String email) throws IOException {
        String linkToInvite = clientUrl + "/verifySecret/" + email;
        UserEntity user = userService.findByEmail(email);
        user.setSecret(getRandomStringOfSize(64));
        System.out.println("New secret is " + user.getSecret());
        MailService.verifyPasswordChange(email, linkToInvite, user.getSecret());
        return ResponseEntity.ok("email for password change is sent!");
    }

    @PostMapping("/update-password/{secret}")
    public ResponseEntity updatePasswordByEmail(@PathVariable("secret") String secret, @RequestBody UpdatePasswordModel model) throws UserNotFoundException {
        UserEntity user = userService.findBySecret(secret);
        if (!Objects.equals(model.getPassword(), model.getrPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        user.setPassword(model.getPassword());
        userService.updateUserEntity(user, user.getNickname());
        return ResponseEntity.ok("Password is updated!");
    }

    @PostMapping("/verify-secret")
    public ResponseEntity verifySecret(@RequestBody UserModelWithSecret userModelWithSecret){
        UserEntity user = userService.findByEmail(userModelWithSecret.getEmail());
        if (!Objects.equals(user.getSecret(), userModelWithSecret.getSecret())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        else return ResponseEntity.ok("Success");
    }

}
