package home.ecommerce.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/products")
public class ProductController {
    @GetMapping("")
    public String showWelcomePage(Principal principal) {
        return "product/product-list";
    }
}
