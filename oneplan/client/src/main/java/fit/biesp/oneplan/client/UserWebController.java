package fit.biesp.oneplan.client;

import fit.biesp.oneplan.client.exception.UserAlreadyExistsException;
import fit.biesp.oneplan.client.models.EventModel;
import fit.biesp.oneplan.client.models.LocationModel;
import fit.biesp.oneplan.client.models.LoginModel;
import fit.biesp.oneplan.client.models.UserRegistrationModel;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Controller
public class UserWebController {
    private final UserClient userClient;
    Long currentId = null;

    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/home")
    public String addHomeRender(Model model ) {
        System.out.println("addhome");
        return "home";
    }



    @GetMapping("/login")
    public String enterRender(Model model) {
        model.addAttribute("loginModel", new LoginModel());
        return "welcome";
    }
    @PostMapping("/login")
    public String enterLogin(Model model, @ModelAttribute LoginModel loginModel) {
        model.addAttribute("loginModel", userClient.login(loginModel));
        model.getAttribute(String.valueOf(currentId));
        return "home";
    }

    @GetMapping("/registration")
    public String addUserRender(Model model ) {
        System.out.println("addnewuser");
        model.addAttribute("userRegistrationDto", new UserRegistrationModel());
        return "register";
    }


    @PostMapping("/registration")
    public String addUserSubmit(Model model, @ModelAttribute UserRegistrationModel userRegistrationModel) {
        System.out.println("addsuserSubmitted");
        try {
            model.addAttribute("userRegistrationDto", userClient.create(userRegistrationModel));
            return "userexists";
        } catch (WebClientResponseException e){
            model.addAttribute("error", e);
            return "userexists";
        }
    }


    @GetMapping("/create-event")
    public String addEventRender(Model eventModel, Model locationModel, Model addlocation) {
        System.out.println("xuy4");
        locationModel.addAttribute("locations", userClient.getLocations());
        eventModel.addAttribute("eventModel", new EventModel());
        return "event";
    }

    @PostMapping("/create-event")
    public String addEventSubmit(Model model, @ModelAttribute EventModel eventModel) {
        System.out.println("xuy3");
        model.addAttribute("eventModel", userClient.createEvent(eventModel));
        return "home";
    }

}
