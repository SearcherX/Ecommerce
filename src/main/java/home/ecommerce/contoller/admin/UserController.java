package home.ecommerce.contoller.admin;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.dto.UserDTO;
import home.ecommerce.entity.Role;
import home.ecommerce.entity.User;
import home.ecommerce.service.UserService;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/admin/users")
@RolesAllowed("ROLE_ADMIN")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String showUsersList(Model model) {
        model.addAttribute("usersList", userService.listAllUsers());
        return "admin/user/user-list";
    }

    @GetMapping("/new")
    public String showUserForm(UserDTO userDTO, Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("role", Role.CUSTOMER);
        model.addAttribute("rolesList", Role.values());
        return "admin/user/user-form";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("action", "update");
        User user = userService.findById(id);
        UserDTO userDTO = userService.toDTO(user);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("role", user.getRole());
        model.addAttribute("rolesList", Role.values());
        return "admin/user/user-form";
    }

    @PostMapping("/save")
    public String save(UserDTO userDTO, @RequestParam String action, @RequestParam @Nullable Long id,
                       @RequestParam Role role) {
        if (action.equals("create"))
            userService.add(userDTO, role);
        else
            userService.update(userDTO, role, id);

        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

}
