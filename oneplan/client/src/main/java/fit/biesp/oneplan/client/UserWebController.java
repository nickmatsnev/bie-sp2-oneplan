package fit.biesp.oneplan.client;

import fit.biesp.oneplan.client.exception.UserNotFoundException;
import fit.biesp.oneplan.client.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Controller /// Controller class for the client, to get access from requests from browser
public class UserWebController {
    private final UserClient userClient;
    String errormsg;
    LoginModel currentUser; /// model for the user login, saves the currently working user.
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
        model.addAttribute("OnSubmitError",errormsg);
        return "welcome";
    }

    @PostMapping("/login") /// post mapping for sending to the server
    public String enterLogin(Model model, @ModelAttribute LoginModel loginModel) throws UserNotFoundException {
        currentUser = loginModel; /// assignment of the credentials to the current user
        /// attribute sending the loginModel to the server
        try {
            model.addAttribute("loginModel", userClient.login(loginModel));
            errormsg = "";
            return "redirect:/home";
        } catch (Exception e) {
            throw new UserNotFoundException("not found");
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleAllException(UserNotFoundException ex) {
        errormsg = "Invalid Username or Password";
        return "redirect:/login";
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

        if (userRegistrationModel.getPassword().length()<8)
        {
            model.addAttribute("userRegistrationSubmit", "Password should be at least 8 characters");
            model.addAttribute("userRegistrationDto", new UserRegistrationModel());
            return "register";
        }

        //      Checks each character to see if it is acceptable.
        for (int i = 0; i < userRegistrationModel.getPassword().length(); i++){
            char c = userRegistrationModel.getPassword().charAt(i);

            if (       ('a' <= c && c <= 'z') // Checks if it is a lower case letter
                    || ('A' <= c && c <= 'Z') //Checks if it is an upper case letter
                    || ('0' <= c && c <= '9')){ //Checks to see if it is a digit

                model.addAttribute("userRegistrationSubmit", "Password should contain at least 1 lower case, 1 uppercase letter and 1 digit");
                model.addAttribute("userRegistrationDto", new UserRegistrationModel());
                return "register";
            }
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
    public String getProfileRender(Model model, Model model1) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// attribute to get the user nickname into profile page
        model1.addAttribute("invitationDTO", new InvitationDTO());
        model.addAttribute("user", userClient.getOneUser(currentUser.getNickname()));
        return "profile";
    }

    @PostMapping("/get-events") /// mapping to open event details
    public String getEventDetails(Model model, @ModelAttribute EventModel eventModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        System.out.println(eventModel.getId() + "This is a Id of the printed value in event list");
        /// getting the event model from the server
        return "eventDetails";
    }

    @GetMapping("/get-one-event/{id}") /// mapping to open event details
    public String getOneEvent(Model model, @ModelAttribute EventModel eventModel, @PathVariable("id") Long id) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// getting the event model from the server
        model.addAttribute("details", userClient.getOneEvent(id));

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


    @GetMapping("/sendemail/{id}")
    // блять я не знаю в @ModelAttribute какая модель, можешь туда встать friend, user, person модели
    public String sendInvitationPageMapping(Model model, @ModelAttribute InvitationModel invitationModel, @PathVariable("id") Long id) {
        List<InvitationModel> invitationModelList = (List<InvitationModel>) userClient.getInvites();
        InvitationModel ourInvite = new InvitationModel();
        // как я это вижу: мы посылаем хэш и потом проверяем на клиенте если совпадает
        // то принтим аксепт и режект кнопки а если нет то редирект нахуй
        for(InvitationModel inv : invitationModelList){
            int hash = 7;
            hash = 31 * hash + (int) inv.getUserId();
            hash = 31 * hash + (inv.getReceiverEmail() == null ? 0 : inv.getReceiverEmail().hashCode());
            if(hash == id){
                ourInvite = inv;
                model.addAttribute("invitation", ourInvite);
                return "invitePage";
            }
        }
        // здесь надо вызвать функцию по сбору АПИ в UserClien. а что туда передавать я хз, для примера сделал invitationModel а так можно что угодно
        return "redirect:/login";
    }

    @PostMapping("/sendemail")
    public String sendInvitationToTheBackEnd(Model model, @ModelAttribute InvitationDTO invitationModel){
        System.out.println(invitationModel.getRecipient_email() + "this is email");
        System.out.println(invitationModel.getSender_id() + "this is Id");
        model.addAttribute("invitationDTO", userClient.createInvite(invitationModel));
        return "invitePage";
    }

    @PostMapping("/sendemail/{id}/accept")
    public String sendInvitationToTheBackEndPositive(Model model, @ModelAttribute InvitationDTO invitationModel, @PathVariable("id") Integer id){
        model.addAttribute("invite", userClient.accept(id));
        return "redirect:/welcome";
    }

    @PostMapping("/sendemail/{id}/reject")
    public String sendInvitationToTheBackEndNegative(Model model, @ModelAttribute InvitationDTO invitationModel, @PathVariable("id") Integer id){
        model.addAttribute("invite", userClient.reject(id));
        return "redirect:/welcome";
    }
}
