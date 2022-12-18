package home.ecommerce.contoller;

import home.ecommerce.entity.Product;
import home.ecommerce.entity.User;
import home.ecommerce.service.BucketService;
import home.ecommerce.service.ProductService;
import home.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {
    private final ProductService productService;
    private final BucketService bucketService;
    private final UserService userService;

    @GetMapping("/")
    public String showSimpleFilteredProducts(@RequestParam(required = false, defaultValue = "", name = "str") String str,
                                             @RequestParam(defaultValue = "1", name = "offset") Integer offset,
                                             Model model, Principal principal) {
        Page<Product> productPage;
        if (str == null || str.isEmpty())
            productPage = productService.findSortedAll(offset);
        else
            productPage = productService.findBySimpleFilterWithMainImage(str, offset);

        model.addAttribute("offset", offset);
        model.addAttribute("simpleFilter", str);
        model.addAttribute("productPage", productPage);
        model.addAttribute("pageNumbers", productService.getPageNumbers(productPage, offset));

        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("bucketItems", bucketService.findByUser(user).getBucketItems());
        }
        return "search/product-list";
    }
}
