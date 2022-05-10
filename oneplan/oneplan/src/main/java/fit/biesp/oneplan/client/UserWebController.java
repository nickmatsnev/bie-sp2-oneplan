package fit.biesp.oneplan.client;

import fit.biesp.oneplan.model.EventModel;
import fit.biesp.oneplan.model.UserRegistrationModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserWebController {
    private final UserClient userClient;

    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/home")
    public String addHomeRender(Model model ) {
        System.out.println("addhome");
        return "home";
    }

    @GetMapping("/users")
    public String redirectWithUsingForwardPrefix(Model model) {
        model.addAttribute("attribute", "message");
        return "welcome";
    }

    @GetMapping("")
    public String enterRender() {
        return "welcome";
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
        model.addAttribute("userRegistrationDto", userClient.create(userRegistrationModel));
        return "welcome";
    }

    @GetMapping("/create-event")
    public String addEventRender(Model model) {
        System.out.println("xuy4");
        model.addAttribute("eventModel", new EventModel());
        return "event";
    }

    @PostMapping("/create-event")
    public String addEventSubmit(Model model, @ModelAttribute EventModel eventModel) {
        System.out.println("xuy3");
        model.addAttribute("eventModel", userClient.createEvent(eventModel));
        return "home";
    }
}
