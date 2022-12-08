package home.ecommerce.service;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.dto.UserDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.User;
import home.ecommerce.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<Category> listAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category add(CategoryDTO categoryDTO) {
        Category category = new Category();
        modelMapper.map(categoryDTO, category);
        return save(category);
    }

    public void deleteCategory(Long id) {
        Optional<Category> deleted = categoryRepository.findById(id);
        deleted.ifPresent(categoryRepository::delete);
    }
}
