package home.ecommerce.contoller.admin;

import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.service.CategoryService;
import home.ecommerce.service.SubcategoryService;
import jakarta.annotation.Nullable;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/admin/subcategories")
@RolesAllowed("ROLE_ADMIN")
@AllArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;

    @GetMapping("")
    public String showSubcategories(Model model) {
        model.addAttribute("subcategoryList", subcategoryService.listAllSubcategories());
        model.addAttribute("categoriesCount", categoryService.count());
        return "admin/subcategory/subcategory-list";
    }

    @GetMapping("/new")
    public String showSubcategoryForm(SubcategoryDTO subcategoryDTO, Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("subcategoryDTO", subcategoryDTO);
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "admin/subcategory/subcategory-form";
    }

    @GetMapping("/update/{id}")
    public String showUpdateSubcategoryForm(@PathVariable("id") Long id, Model model) throws FileNotFoundException {
        model.addAttribute("action", "update");
        SubcategoryDTO subcategoryDTO =  subcategoryService.toDTO(subcategoryService.findById(id));
        model.addAttribute("subcategoryDTO", subcategoryDTO);
        model.addAttribute("categoriesList", categoryService.listAllCategories());
        return "admin/subcategory/subcategory-form";
    }

    @PostMapping("/save")
    public String save(SubcategoryDTO subcategoryDTO, @RequestParam String action, @RequestParam @Nullable Long id) {
        if (action.equals("create"))
            subcategoryService.add(subcategoryDTO);
        else
            subcategoryService.update(subcategoryDTO, id);
        return "redirect:/admin/subcategories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        subcategoryService.deleteSubcategory(id);
        return "redirect:/admin/subcategories";
    }
}
