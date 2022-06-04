package fit.biesp.oneplan.client;

import fit.biesp.oneplan.client.models.EventModel;
import fit.biesp.oneplan.client.models.LocationModel;
import fit.biesp.oneplan.client.models.LoginModel;
import fit.biesp.oneplan.client.models.UserRegistrationModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Controller
public class UserWebController {
    private final UserClient userClient;
    LoginModel currentUser;
    Long eventId;
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
        model.addAttribute("username", currentUser);
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
        model.addAttribute("loginModel", userClient.login(loginModel));
        return "redirect:/home";
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
        if (!Objects.equals(userRegistrationModel.getPassword(), userRegistrationModel.getPasswordConfirm())){
            model.addAttribute("userRegistrationSubmit", "Passwords don't match");
            model.addAttribute("userRegistrationDto", new UserRegistrationModel());
            return "register";
        }
        model.addAttribute("userRegistrationSubmit", userClient.create(userRegistrationModel));
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

    @GetMapping("/get-location")
    public String getLocationRender(Mono<LocationModel> locationModel, Model model) {
        if (currentUser == null){
            return "redirect:/login";
        }
        locationModel = userClient.getOneLocation(186L);
        model.addAttribute("locations", locationModel);
        return "showloc";
    }

    @GetMapping("/get-events")
    public String getUserEvents(Model model) {
        if (currentUser == null){
            return "redirect:/login";
        }
        //Flux<EventModel> eventsModel = userClient.getUserEvents(currentUser.getNickname());
        model.addAttribute("events", userClient.getUserEvents(currentUser.getNickname()));
        model.addAttribute("eventId", eventId);
        return "eventsList";
    }
    @GetMapping("/profile")
    public String getProfileRender() {
        if (currentUser == null){
            return "redirect:/login";
        }
        return "profile";
    }

    @PostMapping("/get-events")
    public String getEventDetails(Model model, @ModelAttribute EventModel eventModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        model.addAttribute("details", userClient.getOneEvent(eventModel.getId()));
        model.addAttribute("eventId", eventId);
        return "eventDetails";
    }

    @GetMapping("/logout")
    public String profileLogout() {
        currentUser = null;
        return "redirect:/login";
    }
}
