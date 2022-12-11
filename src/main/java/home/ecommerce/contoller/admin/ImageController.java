package home.ecommerce.contoller.admin;

import home.ecommerce.dto.ProductDTO;
import home.ecommerce.entity.Product;
import home.ecommerce.service.ImageService;
import home.ecommerce.service.ProductService;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products/{id}/images")
@RolesAllowed("ROLE_ADMIN")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ProductService productService;

    @GetMapping("")
    public String showImages(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("imagesList", product.getFiles());
        return "admin/product/image/image-list";
    }

    @GetMapping("/new")
    public String showImageForm(Model model, @PathVariable Long id) {
        model.addAttribute("action", "create");
        return "admin/product/image/image-form";
    }

    @GetMapping("/update/{id2}")
    public String showUpdateImageForm(Model model, @PathVariable Long id, @PathVariable Long id2) {
        model.addAttribute("action", "update");
        return "admin/product/image/image-form";
    }

    @PostMapping("/save")
    public String save(@RequestParam String action, @RequestParam MultipartFile file,
                       @RequestParam @Nullable Long id2, @PathVariable Long id) {
        Product product = productService.findById(id);
        if (action.equals("create"))
            imageService.add(file, product);
        else
            imageService.update(file, id2, product);
        return "redirect:/admin/products/{id}/images";
    }

    @GetMapping("/delete/{id2}")
    public String deleteCategory(@PathVariable Long id2) {
        imageService.delete(id2);
        return "redirect:/admin/products/{id}/images";
    }
}
