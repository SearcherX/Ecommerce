package home.ecommerce.contoller;

import home.ecommerce.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    private final CategoryService categoryService;

    @GetMapping("")
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("/catalog")
    public String showProducts(Model model) {
        model.addAttribute("catalog", categoryService.listAllCategories());
        return "product/catalog";
    }
}
