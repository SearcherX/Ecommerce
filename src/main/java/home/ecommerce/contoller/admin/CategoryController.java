package home.ecommerce.contoller.admin;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.dto.UserDTO;
import home.ecommerce.service.CategoryService;
import home.ecommerce.service.StorageService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/categories")
@RolesAllowed("ROLE_ADMIN")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final StorageService storageService;

    @GetMapping("")
    public String showCategories(Model model) {
        model.addAttribute("categoryList", categoryService.listAllCategories());
        return "admin/category/category-list";
    }

    @GetMapping("/new")
    public String showCategoryForm(CategoryDTO categoryDTO, Model model) {
        model.addAttribute("categoryDTO", categoryDTO);
        return "admin/category/category-form";
    }

    @PostMapping("/save")
    public String save(CategoryDTO categoryDTO, @RequestParam("file") MultipartFile file) {
        String fileName = storageService.uploadFile(file);
        categoryDTO.setFileName(fileName);
        categoryService.add(categoryDTO);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }
}
