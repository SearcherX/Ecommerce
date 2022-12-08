package home.ecommerce.contoller.admin;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.service.CategoryService;
import home.ecommerce.service.StorageService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

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
        model.addAttribute("action", "create");
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("file", null);
        return "admin/category/category-form";
    }

    @GetMapping("/update/{id}")
    public String showUpdateCategoryForm(@PathVariable("id") Long id, Model model) throws FileNotFoundException {
        model.addAttribute("action", "update");
        CategoryDTO categoryDTO =  categoryService.toDTO(categoryService.findById(id));
        model.addAttribute("categoryDTO", categoryDTO);

        model.addAttribute("file", categoryDTO.getFileName());
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
