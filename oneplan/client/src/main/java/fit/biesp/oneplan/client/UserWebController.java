package fit.biesp.oneplan.client;

import fit.biesp.oneplan.client.exception.UserNotFoundException;
import fit.biesp.oneplan.client.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.WebSession;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller /// Controller class for the client, to get access from requests from browser
public class UserWebController {
    private final UserClient userClient;
    String errormsg;

    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("")     /// a mapping when the user just types in the address
    public String emptyUrl(Model model, WebSession session) {
        /// if user is not logged in, redirects to the login page
        if ((LoginModel) session.getAttribute("user") == null) {
            return "redirect:/login";
        } else {
            return "redirect:/home";
        }
    }


    @GetMapping("/login") /// get mapping for the login page display
    public String enterRender(Model model, WebSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/home";
        }
        model.addAttribute("loginModel", new LoginModel());
        model.addAttribute("OnSubmitError", errormsg);
        return "welcome";
    }

    @PostMapping("/login") /// post mapping for sending to the server
    public String enterLogin(Model model, @ModelAttribute LoginModel loginModel, WebSession session) throws UserNotFoundException {


        /// attribute sending the loginModel to the server
        try {
            model.addAttribute("loginModel", userClient.login(loginModel));

            errormsg = "";
            session.getAttributes().put("user", loginModel);
            session.save();
            return "redirect:/home";
        } catch (Exception e ) {
            throw new UserNotFoundException("not found");
        }
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleAllException(UserNotFoundException ex) {
        errormsg = "Invalid Username or Password or Verify Your Email";
        return "redirect:/login";
    }

    @GetMapping("/home")    /// mapping for the home page
    public String addHomeRender(Model model, WebSession session ) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        /// model attribute for the display of the username of current user
        model.addAttribute("username", (LoginModel) session.getAttribute("user"));
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
    public String addLocationRender(Model model, WebSession session ) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        /// create a new Location Model to fill it in
        model.addAttribute("locationModel", new LocationModel());
        return "addLocation";
    }


    @PostMapping("/create-location") /// post mapping to pass the location model to the server
    public String addLocationSubmit(Model model, @ModelAttribute LocationModel locationModel) {

        /// passing the model to the createLocation in UserClient
        model.addAttribute("locationModel", userClient.createLocation(locationModel));
        return "locationCreated";
    }

    @GetMapping("/create-event") /// get mapping to get html for event creation page
    public String addEventRender(Model eventModel, Model locationModels, Model userid, WebSession session) {

        /// attribute to get the user ID
        LoginModel loginModel = session.getAttribute("user");
        userid.addAttribute("userId", loginModel.getNickname() );
        eventModel.addAttribute("username", (LoginModel) session.getAttribute("user"));
        /// attribute to display all available location
        locationModels.addAttribute("locations", userClient.getLocations());
        /// attribute new event model to pass it to server
        eventModel.addAttribute("eventModel", new EventModel());
        return "event";
    }

    @PostMapping("/create-event") /// post mapping to pass the event model to the server
    public String addEventSubmit(Model model, @ModelAttribute EventModel eventModel) {

        /// passing the event model to the User Client, Create Event
        model.addAttribute("eventModel", userClient.createEvent(eventModel));
        return "eventCreated";
    }

    @GetMapping("/get-events") /// mapping to get the events for each user
    public String getUserEvents(Model model, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        /// attribute to get user events by user nickname
        model.addAttribute("events", userClient.getUserEvents(loginModel.getNickname()));
        return "eventsList";
    }
    @GetMapping("/profile") /// ьфззштп ещ пуе зкщашду зфпу
    public String getProfileRender(Model model, Model model1, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        /// attribute to get the user nickname into profile page
        model1.addAttribute("invitationDTO", new InvitationDTO());
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));
        model.addAttribute("invites", userClient.getEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesAccepted", userClient.getAcceptedEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesRejected", userClient.getRejectedEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesPending", userClient.getPendingEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("myInvites", userClient.getInvitesToEventBySender(loginModel.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(loginModel.getNickname()));
        model.addAttribute("friendModel", new FriendCreateModel());
        model.addAttribute("friendModelDelete", new FriendCreateModel());
        model.addAttribute("allUsers", userClient.getAllUsers());
        System.out.println("Reached getMapping on Profile");
        return "profile";
    }
    @GetMapping("/get-my-invites") /// ьфззштп ещ пуе зкщашду зфпу
    public String getMyInvitesRender(Model model, Model model1, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        /// attribute to get the user nickname into profile page
        model1.addAttribute("invitationDTO", new InvitationDTO());
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));
        model.addAttribute("invites", userClient.getEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesAccepted", userClient.getAcceptedEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesRejected", userClient.getRejectedEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesPending", userClient.getPendingEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("myInvites", userClient.getInvitesToEventBySender(loginModel.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(loginModel.getNickname()));
        model.addAttribute("friendModel", new FriendCreateModel());
        model.addAttribute("friendModelDelete", new FriendCreateModel());
        model.addAttribute("allUsers", userClient.getAllUsers());
        System.out.println("Reached getMapping on myInvites");
        return "myInvites";
    }
    @GetMapping("/get-my-friends") /// ьфззштп ещ пуе зкщашду зфпу
    public String getMyFriendsRender(Model model, Model model1, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        /// attribute to get the user nickname into profile page
        model1.addAttribute("invitationDTO", new InvitationDTO());
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));
        model.addAttribute("invites", userClient.getEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesAccepted", userClient.getAcceptedEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesRejected", userClient.getRejectedEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("invitesPending", userClient.getPendingEventInvitesByRecipientNickname(loginModel.getNickname()));
        model.addAttribute("myInvites", userClient.getInvitesToEventBySender(loginModel.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(loginModel.getNickname()));
        model.addAttribute("friendModel", new FriendCreateModel());
        model.addAttribute("friendModelDelete", new FriendCreateModel());
        model.addAttribute("allUsers", userClient.getAllUsers());
        System.out.println("Reached getMapping on my network");
        return "myNetwork";
    }

    @PostMapping("/get-events") /// mapping to open event details
    public String getEventDetails(Model model, @ModelAttribute EventModel eventModel, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        System.out.println(eventModel.getId() + "This is a Id of the printed value in event list");
        /// getting the event model from the server
        return "eventDetails";
    }

    @GetMapping("/get-one-event/{id}") /// mapping to open event details
    public String getOneEvent(Model model, @ModelAttribute EventModel eventModel, @PathVariable("id") Long id, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        /// getting the event model from the server
        model.addAttribute("username", loginModel);
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));
        model.addAttribute("friends", userClient.getFriendsById(loginModel.getNickname()));
        model.addAttribute("invitedToEvent", userClient.getInvitedToEvent(id));
        model.addAttribute("acceptedToEvent", userClient.getAcceptedEvent(id));
        model.addAttribute("eventInviteModel", new EventInviteRealModel());
        model.addAttribute("details", userClient.getOneEvent(id));

        return "eventDetails";
    }

    @GetMapping("/logout") /// mapping to log out from the system
    public String profileLogout( WebSession session) {
        session.invalidate();
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


    @GetMapping("/sendemail/{id}/{email}")
    // блять я не знаю в @ModelAttribute какая модель, можешь туда встать friend, user, person модели
    public String sendInvitationPageMapping(Model model, @PathVariable("id") int senderId, @PathVariable("email") String recipientEmail) {
        // как я это вижу: мы посылаем хэш и потом проверяем на клиенте если совпадает
        // то принтим аксепт и режект кнопки а если нет то редирект нах
        model.addAttribute("senderId", senderId);
        model.addAttribute("sender", userClient.getOneUserById(senderId));
        model.addAttribute("recipientEmail", recipientEmail);
        return "invitePage";
        // здесь надо вызвать функцию по сбору АПИ в UserClien. а что туда передавать я хз, для примера сделал invitationModel а так можно что угодно
    }

    @PostMapping("/profile")
    public String sendInvitationToTheBackEnd(Model model, @ModelAttribute InvitationDTO invitationDTO, WebSession session){
        System.out.println("Reached postMapping on Profile");
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        model.addAttribute("invitationDTO", userClient.createInvite(invitationDTO));
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));
        model.addAttribute("invites", userClient.getUserInvites(loginModel.getNickname()));
        model.addAttribute("allUsers", userClient.getAllUsers());
        return "redirect:/profile";
    }

    @PostMapping("/get-invites") /// mapping to open event details
    public String getInvites(Model model, @ModelAttribute InvitationWelcomeModel invitationModel, WebSession session) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }

        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        model.addAttribute("invites", userClient.getUserInvites(loginModel.getNickname()));
        /// getting the event model from the server
        return "redirect:/get-my-invites";
    }
    @PostMapping("/delete-friend/{id}/{email}")
    public String deleteFriendSubmit(Model model, WebSession session, @PathVariable("id") int userId, @PathVariable("email") String email){
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("friendModelDelete", userClient.deleteFriend(userId, email));
        return "redirect:/get-my-friends";
    }
    @GetMapping("/get-one-friend/{id}") /// mapping to open patient details
    public String getOneFriend(Model model, WebSession session, @ModelAttribute FriendModel friendModel, @PathVariable("id") int friendId) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        model.addAttribute("friendCurrent", userClient.getOneFriendById(friendId));
        model.addAttribute("friendModel", new FriendModel());
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));

        return "editFriend";
    }
    @PostMapping("/get-one-friend/{id}") /// mapping to open patient details
    public String putOneFriend(Model model, WebSession session, @ModelAttribute FriendModel friendModel, @PathVariable("id") int friendId) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        LoginModel loginModel = (LoginModel) session.getAttribute("user");
        model.addAttribute("friendCurrent", userClient.getOneFriendById(friendId));
        model.addAttribute("friendModel", userClient.updateFriend(friendId, friendModel));
        model.addAttribute("currentUser", userClient.getOneUser(loginModel.getNickname()));

        return "friendUpdated";
    }
    @PostMapping("/add-friend") /// post mapping to pass the event model to the server
    public String addFriendSubmit(Model model, WebSession session, @ModelAttribute FriendCreateModel friendCreateModel) {
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("friendModel", userClient.createFriend(friendCreateModel));
        return "redirect:/get-my-friends";
    }
    @PostMapping("/add-friend-to-event") /// post mapping to pass the eventInvite model to the server
    public String addFriendToEventSubmit(Model model, WebSession session, @ModelAttribute EventInviteRealModel eventInviteModel) {
        System.out.println("add friend to event welcomes you");
        System.out.println(eventInviteModel.getRecipientEmail());
        if ((LoginModel) session.getAttribute("user") == null){
            return "redirect:/login";
        }
        /// passing the event model to the User Client, Create Event
        model.addAttribute("eventInviteModel", userClient.createEventInvite(eventInviteModel));
        return "redirect:/get-events";
    }

    @GetMapping("/accept-inv-event/{email}/{senderid}")
    public String addEventToMine(Model model, @PathVariable("senderid") long senderId, @PathVariable("email") String recipientEmail){
        model.addAttribute("invitationtEventAccept", userClient.acceptInvToEvent(recipientEmail, senderId));
        return "redirect:/get-my-invites";

    }
    @GetMapping("/reject-inv-event/{email}/{senderid}")
    public String rejectInvEvent(Model model, @PathVariable("senderid") long senderId, @PathVariable("email") String recipientEmail){
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
    @GetMapping("/verifyEmail/{email}")
    public String verify(Model model, @PathVariable("email") String email){
        model.addAttribute("verifyModel", new PasswordRecoveryRequestModel());
        model.addAttribute("usersEmail", email);
        return "verifyEmail";
    }
    @PostMapping("/verifyEmail/{email}")
    public String verifyPost(Model model, @PathVariable("email") String email, @ModelAttribute PasswordRecoveryRequestModel pModel){
        model.addAttribute("verifyModel", userClient.verifyEmail(email, pModel));
        model.addAttribute("usersEmail", email);
        return "redirect:/login";
    }
    @GetMapping("/forgotPasswordForm")
    public String sendEmailRender(Model model){
        model.addAttribute("recoveryModel", new PasswordRecoveryRequestModel());
        return "forgotPasswordForm";
    }
    @PostMapping("/forgotPasswordForm")
    public String sendEmail(Model model, @ModelAttribute PasswordRecoveryRequestModel pModel){
        model.addAttribute("recoveryModel", userClient.sendEmailForPassword(pModel));
        return "redirect:/verifySecret/" + pModel.getEmail();
    }
    @GetMapping("/verifySecret/{email}")
    public String sendEmailVerifyRender(Model model, @PathVariable("email") String email){
        model.addAttribute("verifyModel", new UserModelWithSecret());
        model.addAttribute("usersEmail", email);
        return "verifySecret";
    }
    @PostMapping("/verifySecret/{email}")
    public String sendVerifyEmail(Model model, @PathVariable("email") String email, @ModelAttribute UserModelWithSecret userModelWithSecret){
        model.addAttribute("verifyModel", userClient.getSecretVerified(userModelWithSecret));
        model.addAttribute("usersEmail", email);
        return "redirect:/newPassword/" + userModelWithSecret.getSecret();
    }
    @GetMapping("/newPassword/{secret}")
    public String sendEmailRender(Model model, @PathVariable("secret") String secret){
        model.addAttribute("recoveryModel", new UpdatePasswordModel());
        model.addAttribute("usersEmail", secret);
        return "newPassword";
    }
    @PostMapping("/newPassword/{secret}")
    public String sendEmail(Model model, @PathVariable("secret") String secret, @ModelAttribute UpdatePasswordModel pModel){
        model.addAttribute("recoveryModel", userClient.sendNewPassword(pModel, secret));
        model.addAttribute("usersEmail", secret);
        return "redirect:/login";
    }


}
