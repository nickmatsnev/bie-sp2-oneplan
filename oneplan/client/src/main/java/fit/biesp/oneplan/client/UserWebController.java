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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.xml.stream.Location;

@Controller
public class UserWebController {
    private final UserClient userClient;
    String currentId = null;
    LoginModel currentUser;
    LocationModel locatinn;
    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("")
    public String emptyUrl(Model model ) {
        if (currentUser == null) {
            return "redirect:/login";
        } else {
            return "redirect:/home";
        }
    }

    @GetMapping("/home")
    public String addHomeRender(Model model ) {
        if (currentUser == null){
            return "redirect:/login";
        }
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
        currentUser = loginModel;
        try {
            model.addAttribute("loginModel", userClient.login(loginModel));
            return "home";
        } catch (Exception e){
            model.addAttribute("loginModel", e);
            return "redirect:/welcome";
        }

        //return "home";
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
            model.addAttribute("userRegistrationSubmit", userClient.create(userRegistrationModel));
        } catch(Exception e) {
            model.addAttribute("userRegistrationSubmit", e);
        }
        model.addAttribute("userRegistrationDto", new UserRegistrationModel());
        return "register";
    }

    @GetMapping("/create-location")
    public String addLocationRender(Model model ) {
        if (currentUser == null){
            return "redirect:/login";
        }
        System.out.println("addnewuser");
        model.addAttribute("locationModel", new LocationModel());
        return "addLocation";
    }


    @PostMapping("/create-location")
    public String addLocationSubmit(Model model, @ModelAttribute LocationModel locationModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        System.out.println("addsuserSubmitted");
        try {
            model.addAttribute("locationModel", userClient.createLocation(locationModel));
        } catch(HttpClientErrorException.BadRequest e) {
            model.addAttribute("locationModel", e);
        }
        return "locationCreated";
    }

    @GetMapping("/create-event")
    public String addEventRender(Model eventModel, Model locationModels, Model userid) {
        if (currentUser == null){
            return "redirect:/login";
        }
        System.out.println("xuy4");
        userid.addAttribute("userId", userClient.login(currentUser));
        locationModels.addAttribute("locations", userClient.getLocations());
        eventModel.addAttribute("eventModel", new EventModel());
        return "event";
    }

    @PostMapping("/create-event")
    public String addEventSubmit(Model model, @ModelAttribute EventModel eventModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        System.out.println("xuy3");
        model.addAttribute("eventModel", userClient.createEvent(eventModel));
        return "eventCreated";
    }
}
