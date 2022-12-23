package home.ecommerce.contoller;

import home.ecommerce.entity.Bucket;
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
        model.addAttribute("bucket", bucketService.getBucket(user));
        return "bucket/bucket";
    }

    @GetMapping("/checkout")
    public String checkout(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        bucketService.increasePurchaseNumber(user);
        bucketService.deleteALl(user);
        return "bucket/checkout";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addBucket(@RequestParam Long productId, Principal principal) {
        Product product = productService.findById(productId);
        User user = userService.findByUsername(principal.getName());
        bucketService.addItemToBucket(product, user);
        return "ok";
    }

    @PostMapping("/update")
    @ResponseBody
    public Bucket updateBucket(@RequestParam("itemId") Long bucketItemId, @RequestParam("quantity") Integer quantity, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return bucketService.updateItemInBucket(bucketItemId, quantity, user);
    }

    @GetMapping("/delete/{id}")
    public String delProduct(@PathVariable Long id, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        bucketService.deleteItemFromBucket(id, user);
        return "redirect:/bucket";
    }

    @GetMapping("/delete")
    public String delAllProducts(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        bucketService.deleteALl(user);
        return "redirect:/bucket";
    }
}
