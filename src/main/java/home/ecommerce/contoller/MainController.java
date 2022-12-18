package home.ecommerce.contoller;

import home.ecommerce.entity.Product;
import home.ecommerce.service.CategoryService;
import home.ecommerce.service.ProductService;
import home.ecommerce.service.security.CustomAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private final ProductService productService;
    @GetMapping("/")
    public String showWelcomePage(Model model) {
        model.addAttribute("popularProducts", productService.findTop10Popular());
        model.addAttribute("newProducts", productService.findTop10New());
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal, HttpServletRequest request) {
        String referer = request.getHeader("Referer"); // получить предыдущий URL перед логином

        // сохранить URL в сессию
        request.getSession().setAttribute(CustomAuthenticationSuccessHandler.REDIRECT_URL_SESSION_ATTRIBUTE_NAME, referer);

        return principal == null ?  "account/login" : "redirect:/";
    }
}
