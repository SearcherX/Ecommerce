package home.ecommerce.service;

import home.ecommerce.entity.Category;
import home.ecommerce.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public List<Category> listAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }
}
