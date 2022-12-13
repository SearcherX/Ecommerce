package home.ecommerce.contoller;

import home.ecommerce.entity.Product;
import home.ecommerce.entity.User;
import home.ecommerce.service.BucketService;
import home.ecommerce.service.ProductService;
import home.ecommerce.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
@RolesAllowed({"CUSTOMER", "ADMIN"})
@AllArgsConstructor
public class BucketController {
    private final ProductService productService;
    private final BucketService bucketService;
    private final UserService userService;

    @GetMapping("")
    public String showBucket(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("bucket", bucketService.findByUser(user));
        return "bucket/bucket";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addProduct(@RequestParam Long productId, Principal principal) {
        Product product = productService.findById(productId);
        User user = userService.findByUsername(principal.getName());
        bucketService.addItemToBucket(product, user);
        return "ok";
    }
}
