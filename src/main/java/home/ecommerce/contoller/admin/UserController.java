package home.ecommerce.contoller.admin;

import home.ecommerce.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
@RolesAllowed("ROLE_ADMIN")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String showUsersList(Model model) {
        model.addAttribute("usersList", userService.listAllUsers());
        return "admin/user/users-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

}
