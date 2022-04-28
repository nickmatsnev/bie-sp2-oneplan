package fit.biesp.oneplan.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClientWebController  {
    private final RegistrationClient userClient;

    public ClientWebController(RegistrationClient userClient) {
        this.userClient = userClient;
    }



    @GetMapping("/users")
    public String addUserRender(Model model){
        model.addAttribute("userDto", new UserDto());
        return "addUser";
    }

    @PostMapping("/users")
    public String addUserSumbit(Model model, @ModelAttribute UserDto userDto) {
        model.addAttribute("userDto", userClient.create(userDto));
        return "addUser";
    }

    @GetMapping("/users/{nickname}/{password}")
    public String userLoginSubmit(Model model, @ModelAttribute UserDto userDto){
        model.addAttribute("userLoginto", userClient.getUser());
        return "login";
    }


}
