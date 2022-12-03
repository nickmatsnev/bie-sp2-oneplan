package fit.biesp.oneplan.client;

import fit.biesp.oneplan.client.exception.UserNotFoundException;
import fit.biesp.oneplan.client.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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

//        //      Checks each character to see if it is acceptable.
//        for (int i = 0; i < userRegistrationModel.getPassword().length(); i++){
//            char c = userRegistrationModel.getPassword().charAt(i);
//
//            if (       ('a' <= c && c <= 'z') // Checks if it is a lower case letter
//                    || ('A' <= c && c <= 'Z') //Checks if it is an upper case letter
//                    || ('0' <= c && c <= '9')){ //Checks to see if it is a digit
//
//                model.addAttribute("userRegistrationSubmit", "Password should contain at least 1 lower case, 1 uppercase letter and 1 digit");
//                model.addAttribute("userRegistrationDto", new UserRegistrationModel());
//                return "register";
//            }
//        }
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
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));
        model.addAttribute("invites", userClient.getEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesAccepted", userClient.getAcceptedEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesRejected", userClient.getRejectedEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesPending", userClient.getPendingEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("myInvites", userClient.getInvitesToEventBySender(currentUser.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(currentUser.getNickname()));
        model.addAttribute("friendModel", new FriendCreateModel());
        model.addAttribute("friendModelDelete", new FriendCreateModel());
        model.addAttribute("allUsers", userClient.getAllUsers());
        System.out.println("Reached getMapping on Profile");
        return "profile";
    }
    @GetMapping("/get-my-invites") /// ьфззштп ещ пуе зкщашду зфпу
    public String getMyInvitesRender(Model model, Model model1) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// attribute to get the user nickname into profile page
        model1.addAttribute("invitationDTO", new InvitationDTO());
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));
        model.addAttribute("invites", userClient.getEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesAccepted", userClient.getAcceptedEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesRejected", userClient.getRejectedEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesPending", userClient.getPendingEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("myInvites", userClient.getInvitesToEventBySender(currentUser.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(currentUser.getNickname()));
        model.addAttribute("friendModel", new FriendCreateModel());
        model.addAttribute("friendModelDelete", new FriendCreateModel());
        model.addAttribute("allUsers", userClient.getAllUsers());
        System.out.println("Reached getMapping on myInvites");
        return "myInvites";
    }
    @GetMapping("/get-my-friends") /// ьфззштп ещ пуе зкщашду зфпу
    public String getMyFriendsRender(Model model, Model model1) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// attribute to get the user nickname into profile page
        model1.addAttribute("invitationDTO", new InvitationDTO());
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));
        model.addAttribute("invites", userClient.getEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesAccepted", userClient.getAcceptedEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesRejected", userClient.getRejectedEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("invitesPending", userClient.getPendingEventInvitesByRecipientNickname(currentUser.getNickname()));
        model.addAttribute("myInvites", userClient.getInvitesToEventBySender(currentUser.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(currentUser.getNickname()));
        model.addAttribute("friendModel", new FriendCreateModel());
        model.addAttribute("friendModelDelete", new FriendCreateModel());
        model.addAttribute("allUsers", userClient.getAllUsers());
        System.out.println("Reached getMapping on my network");
        return "myNetwork";
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
        model.addAttribute("username", currentUser);
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(currentUser.getNickname()));
        model.addAttribute("eventInviteModel", new EventInviteRealModel());
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
    public String sendInvitationPageMapping(Model model, @ModelAttribute InvitationWelcomeModel invitationModel, @PathVariable("id") int id) {
        Mono<InvitationWelcomeModel> ourInvite = userClient.getInvite(id);
        // как я это вижу: мы посылаем хэш и потом проверяем на клиенте если совпадает
        // то принтим аксепт и режект кнопки а если нет то редирект нах
        model.addAttribute("invitation", ourInvite);
        return "invitePage";
        // здесь надо вызвать функцию по сбору АПИ в UserClien. а что туда передавать я хз, для примера сделал invitationModel а так можно что угодно
    }

    @PostMapping("/profile")
    public String sendInvitationToTheBackEnd(Model model, @ModelAttribute InvitationDTO invitationDTO){
        System.out.println("Reached postMapping on Profile");
        model.addAttribute("invitationDTO", userClient.createInvite(invitationDTO));
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));
        model.addAttribute("invites", userClient.getUserInvites(currentUser.getNickname()));
        model.addAttribute("allUsers", userClient.getAllUsers());
        return "redirect:/profile";
    }

    @PostMapping("/sendemail/{id}/accept")
    public String sendInvitationToTheBackEndPositive(Model model, @ModelAttribute InvitationModel invitationModel, @PathVariable("id") Integer id){
        model.addAttribute("invite", userClient.accept(id));
        model.addAttribute("invitation", invitationModel);
        return "redirect:/welcome";
    }

    @PostMapping("/sendemail/{id}/reject")
    public String sendInvitationToTheBackEndNegative(Model model, @ModelAttribute InvitationModel invitationModel, @PathVariable("id") Integer id){
        model.addAttribute("invite", userClient.reject(id));
        model.addAttribute("invitation", invitationModel);
        return "redirect:/welcome";
    }
    @PostMapping("/get-invites") /// mapping to open event details
    public String getInvites(Model model, @ModelAttribute InvitationWelcomeModel invitationModel) {
        if (currentUser == null){
            return "redirect:/login";
        }

        model.addAttribute("invites", userClient.getUserInvites(currentUser.getNickname()));
        /// getting the event model from the server
        return "redirect:/get-my-invites";
    }
    @PostMapping("/delete-friend/{id}/{email}")
    public String deleteFriendSubmit(Model model, @PathVariable("id") int userId, @PathVariable("email") String email){
        if (currentUser == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("friendModelDelete", userClient.deleteFriend(userId, email));
        return "redirect:/get-my-friends";
    }
    @GetMapping("/get-one-friend/{id}") /// mapping to open patient details
    public String getOneFriend(Model model, @ModelAttribute FriendModel friendModel, @PathVariable("id") int friendId) {
        if (currentUser == null){
            return "redirect:/login";
        }
        model.addAttribute("friendCurrent", userClient.getOneFriendById(friendId));
        model.addAttribute("friendModel", new FriendModel());
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));

        return "editFriend";
    }
    @PostMapping("/get-one-friend/{id}") /// mapping to open patient details
    public String putOneFriend(Model model, @ModelAttribute FriendModel friendModel, @PathVariable("id") int friendId) {
        if (currentUser == null){
            return "redirect:/login";
        }
        model.addAttribute("friendCurrent", userClient.getOneFriendById(friendId));
        model.addAttribute("friendModel", userClient.updateFriend(friendId, friendModel));
        model.addAttribute("currentUser", userClient.getOneUser(currentUser.getNickname()));

        return "friendUpdated";
    }
    @PostMapping("/add-friend") /// post mapping to pass the event model to the server
    public String addFriendSubmit(Model model, @ModelAttribute FriendCreateModel friendCreateModel) {
        if (currentUser == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("friendModel", userClient.createFriend(friendCreateModel));
        return "redirect:/get-my-friends";
    }
    @PostMapping("/add-friend-to-event") /// post mapping to pass the eventInvite model to the server
    public String addFriendToEventSubmit(Model model, @ModelAttribute EventInviteRealModel eventInviteModel) {
        System.out.println("add friend to event welcomes you");
        System.out.println(eventInviteModel.getRecipientEmail());
        if (currentUser == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("eventInviteModel", userClient.createEventInvite(eventInviteModel));
        return "redirect:/get-events";
    }

    @GetMapping("/accept-inv-event/{email}/{senderid}")
    public String addEventToMine(Model model, @PathVariable("senderid") int senderId, @PathVariable("email") String recipientEmail){
        model.addAttribute("invitationtEventAccept", userClient.acceptInvToEvent(recipientEmail, senderId));
        return "redirect:/get-my-invites";

    }
    @GetMapping("/reject-inv-event/{email}/{senderid}")
    public String rejectInvEvent(Model model, @PathVariable("senderid") int senderId, @PathVariable("email") String recipientEmail){
        model.addAttribute("invitationtEventReject", userClient.rejectInvToEvent(recipientEmail, senderId));
        return "redirect:/get-my-invites";
    }
    @GetMapping("/delete-inv-event/{email}/{senderid}")
    public String deleteInvEvent(Model model, @PathVariable("senderid") int senderId, @PathVariable("email") String recipientEmail){
        model.addAttribute("invitationtEventDelete", userClient.deleteInvToEvent(recipientEmail, senderId));
        return "redirect:/get-my-invites";
    }

    @GetMapping("/delete-event/{id}")
    public String deleteEvent(Model model, @PathVariable("id") Long eventId){
        model.addAttribute("deleteEvent", userClient.deleteEvent(eventId));
        return "redirect:/get-events";
    }
}
