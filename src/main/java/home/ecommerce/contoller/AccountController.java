package home.ecommerce.contoller;

import home.ecommerce.dto.UserDTO;
import home.ecommerce.entity.User;
import home.ecommerce.entity.VerificationToken;
import home.ecommerce.service.UserService;
import home.ecommerce.service.VerificationTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;

@Controller
@AllArgsConstructor
public class AccountController {
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    private final UserService userService;
    private final VerificationTokenService verificationTokenService;

//    public AccountController(UserService userService) {
//        this.userService = userService;
//    }

    @InitBinder
    public void initBInder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/register")
    public String register(UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        return "layout/register";
    }

    @PostMapping("/register")
    public String save(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes ra) {
        if (userService.findByUsername(userDTO.getUsername()) != null) {
            bindingResult.addError(new FieldError("userDTO", "username",
                    "User already in use"));
        }

        if (userService.findByEmail(userDTO.getEmail()) != null) {
            bindingResult.addError(new FieldError("userDTO", "email",
                    "Email already in use"));
        }

        if (userDTO.getPassword() != null && userDTO.getRpassword() != null) {
            if (!userDTO.getPassword().equals(userDTO.getRpassword())) {
                bindingResult.addError(new FieldError("userDTO", "rpassword",
                        "Password must match"));
            }
        }

        if (bindingResult.hasErrors()) {
            return "layout/register";
        }

        ra.addFlashAttribute("message",
                "Success! A verification email has been sent to your email address");
        userService.register(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null) {
            model.addAttribute("message", "Your verification token is invalid");
        } else {
            User user = verificationToken.getUser();

            if (!user.isEnabled()) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                if (verificationToken.getExpireDate().before(currentTimestamp)) {
                    model.addAttribute("message", "Your verification token has expired");
                } else {
                    user.setEnabled(true);
                    userService.save(user);
                    model.addAttribute("message", "Your account is successfully activated");
                }
            } else {
                model.addAttribute("message", "Your account is already activated");
            }
        }

        return "layout/activation";
    }
}
