package fit.biesp.oneplan.client;

import fit.biesp.oneplan.client.exception.UserNotFoundException;
import fit.biesp.oneplan.client.models.EventModel;
import fit.biesp.oneplan.client.models.LocationModel;
import fit.biesp.oneplan.client.models.LoginModel;
import fit.biesp.oneplan.client.models.UserRegistrationModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Controller /// Controller class for the client, to get access from requests from browser
public class UserWebController {
    private final UserClient userClient;
    LoginModel currentUser; /// model for the user login, saves the currently working user.
    Long eventId;
    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("")     /// a mapping when the user just types in the address
    public String emptyUrl(Model model ) {
        /// if user is not logged in, redirects to the login page
        if (currentUser == null) {
            return "redirect:/login";
        } else {
            return "redirect:/home";
        }
    }


    @GetMapping("/login") /// get mapping for the login page display
    public String enterRender(Model model) {
        /// attribute for the login model, model is username and login
        model.addAttribute("loginModel", new LoginModel());
        return "welcome";
    }

    @PostMapping("/login") /// post mapping for sending to the server
    public String enterLogin(Model model, @ModelAttribute LoginModel loginModel) throws Exception {
        currentUser = loginModel; /// assignment of the credentials to the current user
        /// attribute sending the loginModel to the server
        try {
            model.addAttribute("loginModel", userClient.login(loginModel));
            return "redirect:/home";
        } catch (Exception e) {
            throw new Exception();
        }
    }


    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        return "invalidPassword";
    }

    @GetMapping("/home")    /// mapping for the home page
    public String addHomeRender(Model model ) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// model attribute for the display of the username of current user
        model.addAttribute("username", currentUser);
        return "home";
    }


    @GetMapping("/registration") /// get mapping for the registration page
    public String addUserRender(Model model ) {
        /// attribute that creates empty RegistrationModel
        model.addAttribute("userRegistrationDto", new UserRegistrationModel());
        return "register";
    }

    @PostMapping("/registration") /// post mapping to send the registering user credentials to the sever
    public String addUserSubmit(Model model, @ModelAttribute UserRegistrationModel userRegistrationModel) {
        /// if the password and password verification are not hte same throws error in the http
        if (!Objects.equals(userRegistrationModel.getPassword(), userRegistrationModel.getPasswordConfirm())){
            model.addAttribute("userRegistrationSubmit", "Passwords don't match");
            model.addAttribute("userRegistrationDto", new UserRegistrationModel());
            return "register";
        }
        /// sending the registration model to the server
        model.addAttribute("userRegistrationSubmit", userClient.create(userRegistrationModel));
        model.addAttribute("userRegistrationDto", new UserRegistrationModel());
        return "register";
    }

    @GetMapping("/create-location") /// get mapping to get html for location creation page
    public String addLocationRender(Model model ) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// create a new Location Model to fill it in
        model.addAttribute("locationModel", new LocationModel());
        return "addLocation";
    }


    @PostMapping("/create-location") /// post mapping to pass the location model to the server
    public String addLocationSubmit(Model model, @ModelAttribute LocationModel locationModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// passing the model to the createLocation in UserClient
        model.addAttribute("locationModel", userClient.createLocation(locationModel));
        return "locationCreated";
    }

    @GetMapping("/create-event") /// get mapping to get html for event creation page
    public String addEventRender(Model eventModel, Model locationModels, Model userid) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// attribute to get the user ID
        userid.addAttribute("userId", userClient.login(currentUser));
        /// attribute to display all available location
        locationModels.addAttribute("locations", userClient.getLocations());
        /// attribute new event model to pass it to server
        eventModel.addAttribute("eventModel", new EventModel());
        return "event";
    }

    @PostMapping("/create-event") /// post mapping to pass the event model to the server
    public String addEventSubmit(Model model, @ModelAttribute EventModel eventModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("eventModel", userClient.createEvent(eventModel));
        return "eventCreated";
    }

    @GetMapping("/get-events") /// mapping to get the events for each user
    public String getUserEvents(Model model) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// attribute to get user events by user nickname
        model.addAttribute("events", userClient.getUserEvents(currentUser.getNickname()));
        return "eventsList";
    }
    @GetMapping("/profile") /// ьфззштп ещ пуе зкщашду зфпу
    public String getProfileRender(Model model) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// attribute to get the user nickname into profile page
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/get-events") /// mapping to open event details
    public String getEventDetails(Model model, @ModelAttribute EventModel eventModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// getting the event model from the server
        model.addAttribute("details", userClient.getOneEvent(eventModel.getId()));
        model.addAttribute("eventId", eventId);
        return "eventDetails";
    }

    @GetMapping("/get-one-event") /// mapping to open event details
    public String getOneEvent(Model model, @ModelAttribute EventModel eventModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// getting the event model from the server
        model.addAttribute("details", userClient.getOneEvent(4L));

        return "eventDetails";
    }

    @GetMapping("/logout") /// mapping to log out from the system
    public String profileLogout() {
        currentUser = null;
        return "redirect:/login";
    }

    @GetMapping("/about") /// mapping to get the about page
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/change-password") /// mapping to get the password change page
    public String passwordPage(Model model) {
        model.addAttribute("password", new LoginModel());
        return "about";
    }

    @PostMapping("/change-password") /// mapping to change the password in server
    public String passwordChange(Model model, @ModelAttribute LoginModel loginModel) {
        // need to check for old password and change for the new one, no need in update of nickname
        return "about";
    }
}
