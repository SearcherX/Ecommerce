package home.ecommerce.contoller;

import home.ecommerce.service.CategoryService;
import home.ecommerce.service.security.CustomAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class MainController {
    @GetMapping("")
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal, HttpServletRequest request) throws Exception{
        String referer = request.getHeader("Referer"); //Get previous URL before call '/login'

        //save referer URL to session, for later use on CustomAuthenticationSuccesshandler
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);

        return principal == null ?  "account/login" : "redirect:/";
    }
}
