package home.ecommerce.contoller;

import home.ecommerce.entity.*;
import home.ecommerce.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class CatalogController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ProductService productService;
    private final BucketService bucketService;
    private final UserService userService;
    private final ImageService imageService;

    @GetMapping("/catalog")
    public String showCategories(Model model) {
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "product/product-catalog";
    }

    @GetMapping("/catalog/{cipher}")
    public String showSubcategories(Model model, @PathVariable String cipher) {
        List<Category> categories = categoryService.listAllCategories();
        model.addAttribute("categoriesList", categories);
        Category category = categoryService.findByCipher(categories, cipher);
        model.addAttribute("category", category);
        model.addAttribute("subcategories", categoryService.getSubcategories(category));
        return "product/product-category";
    }

    @GetMapping("/catalog/{cipher}/{cipher2}/{offset}")
    public String showProducts(Model model, @PathVariable String cipher,
                               @PathVariable String cipher2, @PathVariable("offset") Integer offset,
                               Principal principal) {
        List<Category> categories = categoryService.listAllCategories();
        model.addAttribute("categoriesList", categories);

        Category category = categoryService.findByCipher(categories, cipher);
        Subcategory subcategory = subcategoryService.findByCipher(category.getSubcategories(), cipher2);

        model.addAttribute("category", category);
        model.addAttribute("subcategory", subcategory);
        Page<Product> productPage =  productService.findBySubcategoryWithMainImage(subcategory, offset);
        model.addAttribute("productPage",productPage );
        model.addAttribute("pageNumbers", productService.getPageNumbers(productPage, offset));

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("bucketItems", bucketService.findByUser(user).getBucketItems());
        }

        return "product/product-subcategory";
    }

    @GetMapping("/catalog/product/{cipher}")
    public String showProductDetails(Model model, @PathVariable String cipher, Principal principal) {
        Product product = productService.findByCipherFully(cipher);
        model.addAttribute("product", product);
        
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("bucketItems", bucketService.findByUser(user).getBucketItems());
        }

        return "product/product-details";
    }


}
