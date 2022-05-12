package fit.biesp.oneplan.controller;

import fit.biesp.oneplan.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.exception.UserNotFoundException;
import fit.biesp.oneplan.model.UserModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
import fit.biesp.oneplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @PostMapping()
//    public ResponseEntity registration(UserRegistrationModel userModel){
//        System.out.println("xuy");
//        try{
//            userService.registration(userModel);
//            return ResponseEntity.ok("User created!");
//        } catch (UserAlreadyExistsException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body("Error");
//        }
//    }

    @PostMapping()
    public ResponseEntity registration(@RequestBody UserRegistrationModel userModel){
        try{
            userService.registration(userModel);
            return ResponseEntity.ok(true);
        } catch (UserAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

}
