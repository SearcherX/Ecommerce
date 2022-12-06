package home.ecommerce.contoller;

import home.ecommerce.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RolesAllowed("ROLE_ADMIN")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showUsersList(Model model) {
        model.addAttribute("usersList", userService.listAllUsers());
        return "user/users-list";
    }

}
