package home.ecommerce.contoller;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.entity.User;
import home.ecommerce.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class CatalogController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ProductService productService;
    private final BucketService bucketService;
    private final UserService userService;

    @GetMapping("/catalog")
    public String showCategories(Model model) {
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "product/product-catalog";
    }

    @GetMapping("/catalog/{cipher}")
    public String showSubcategories(Model model, @PathVariable String cipher) {
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        Category category = categoryService.findByCipher(cipher);
        model.addAttribute("category", category);
        return "product/product-category";
    }

    @GetMapping("/catalog/{cipher}/{cipher2}")
    public String showProducts(Model model, @PathVariable String cipher, @PathVariable String cipher2, Principal principal) {
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        Subcategory subcategory = subcategoryService.findByCipher(cipher2);
        model.addAttribute("subcategory", subcategory);
        model.addAttribute("productList", productService.findBySubcategory(subcategory));
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("bucket", bucketService.findByUser(user));
        }
        return "product/product-subcategory";
    }


}
