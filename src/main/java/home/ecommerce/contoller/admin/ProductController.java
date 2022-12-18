package home.ecommerce.contoller.admin;

import home.ecommerce.dto.ProductDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.service.CategoryService;
import home.ecommerce.service.ProductService;
import home.ecommerce.service.SubcategoryService;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@RolesAllowed("ROLE_ADMIN")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    @GetMapping("/")
    public String showProducts(Model model, @RequestParam Integer offset) {
        model.addAttribute("productList", productService.findSortedAllWithSubcategory(offset, 30));
        model.addAttribute("subcategoriesCount", subcategoryService.count());
        return "admin/product/product-list";
    }

    @GetMapping("/new")
    public String showProductForm(ProductDTO productDTO, Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "admin/product/product-form";
    }

    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("action", "update");
        ProductDTO productDTO =  productService.toDTO(productService.findById(id));
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "admin/product/product-form";
    }

    @PostMapping("/save")
    public String save(ProductDTO productDTO, @RequestParam String action, @RequestParam @Nullable Long id) {
        if (action.equals("create"))
            productService.add(productDTO);
        else
            productService.update(productDTO, id);
        return "redirect:/admin/products/?offset=1";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products/?offset=1";
    }

    @GetMapping("/subcategories")
    @ResponseBody
    public List<Subcategory> getRegions(@RequestParam Category category) {
        return subcategoryService.findByCategory(category);
    }
}
