package home.ecommerce.contoller;

import home.ecommerce.entity.Category;
import home.ecommerce.service.CategoryService;
import home.ecommerce.service.SubcategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class CatalogController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @GetMapping("/catalog")
    public String showCategories(Model model) {
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "product/product-catalog";
    }

    @GetMapping("/catalog/{cipher}")
    public String showSubcategories(Model model, @PathVariable String cipher) {
        Category category = categoryService.findByCipher(cipher);
        model.addAttribute("category", category);
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "product/product-category";
    }
}
