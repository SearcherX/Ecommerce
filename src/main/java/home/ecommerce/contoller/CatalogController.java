package home.ecommerce.contoller;

import home.ecommerce.dto.FilterDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.entity.User;
import home.ecommerce.service.*;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/catalog")
@AllArgsConstructor
@SessionAttributes({"filter"})
public class CatalogController {
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ProductService productService;
    private final BucketService bucketService;
    private final UserService userService;

    @ModelAttribute("filter")
    public FilterDTO addFilter() {
        return new FilterDTO();
    }

    @GetMapping("")
    public String showCategories(Model model) {
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "product/product-catalog";
    }

    @PostMapping("/search")
    public String saveFilter(Map<String, Object> model, FilterDTO filterDTO, @RequestParam("pn") @Nullable String str) {
        filterDTO.setSearch(true);
        // если вызван простой фильтр
        if (str != null) {
            filterDTO.setNameContains(str);
        }

        model.put("filter", filterDTO);
        return "redirect:/catalog/search?page=1";
    }
    @GetMapping("/search")
    public String showAdvanceFilteredProducts(HttpServletRequest request, Model model, Principal principal,
                                              @RequestParam("page") Integer page) {
        FilterDTO filter = (FilterDTO) request.getSession().getAttribute("filter");

        List<Category> categories = categoryService.listAllCategories();
        model.addAttribute("categoriesList", categories);

        Page<Product> products = productService.findBySearchCriteria(filter, page);
        model.addAttribute("page", page);
        model.addAttribute("productPage", products);
        model.addAttribute("found", CommonUtils.getTotalItemsString(products.getTotalElements()));
        model.addAttribute("pageNumbers", productService.getPageNumbers(products, page));

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("bucketItems", bucketService.findByUser(user).getBucketItems());
        }
        return "product/product-list";
    }

    @GetMapping("/{cipher}")
    public String showSubcategories(Model model, @PathVariable String cipher) {
        List<Category> categories = categoryService.listAllCategories();
        model.addAttribute("categoriesList", categories);
        Category category = categoryService.findByCipher(categories, cipher);
        model.addAttribute("category", category);
        model.addAttribute("subcategories", categoryService.getSubcategories(category));
        return "product/product-category";
    }

    @GetMapping("/{cipher}/{cipher2}/{page}")
    public String showProducts(Map<String, Object> model, @PathVariable String cipher,
                               @PathVariable String cipher2, @PathVariable("page") Integer offset,
                               Principal principal) {
        List<Category> categories = categoryService.listAllCategories();
        model.put("categoriesList", categories);

        Category category = categoryService.findByCipher(categories, cipher);
        Subcategory subcategory = subcategoryService.findByCipher(category.getSubcategories(), cipher2);

        model.put("filter", new FilterDTO()); // сбросить фильтр
        model.put("category", category);
        model.put("subcategory", subcategory);
        Page<Product> products =  productService.findBySubcategoryWithMainImage(subcategory, offset);
        model.put("productPage", products);
        model.put("pageNumbers", productService.getPageNumbers(products, offset));

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.put("bucketItems", bucketService.findByUser(user).getBucketItems());
        }
        return "product/product-list";
    }

    @GetMapping("/product/{cipher}")
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
