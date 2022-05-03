package fit.biesp.oneplan.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Controller
public class UserWebController {
    private final UserClient userClient;

    public UserWebController(UserClient userClient) {
        this.userClient = userClient;
    }


    @GetMapping("/users")
    public String addUserRender(Model model ) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @GetMapping("/users/home")
    public String addHomeRender(Model model ) {
        return "home";
    }

    @PostMapping("/users")
    public String addUserSubmit(Model model, @ModelAttribute UserDto userDto) {
        model.addAttribute("userDto", userClient
                .create(userDto)
        );
        return "home";
    }

    @GetMapping("/events")
    public String addEventRender(Model model) {
        System.out.println("xuy4");
        model.addAttribute("eventDto", new EventDto());
        return "event";
    }

    @PostMapping("/events")
    public String addEventSubmit(Model model, @ModelAttribute EventDto eventDto) {
        System.out.println("xuy3");
        model.addAttribute("eventDto", userClient.createEvent(eventDto));
        return "event";
    }
}
