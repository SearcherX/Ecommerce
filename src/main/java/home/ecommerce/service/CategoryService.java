package home.ecommerce.service;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.CategoryRepository;
import jakarta.persistence.OrderBy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<Category> listAllCategories() {
        List<Category> categories = categoryRepository.findAllByOrderByCategoryNameAsc();
        categories.forEach(category -> Hibernate.initialize(category.getSubcategories()));
        return categories;
    }

    @Transactional
    public Set<Subcategory> getSubcategories(Category category) {
        return category.getSubcategories();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category findByCipher(List<Category> categories, String cipher) {
        for (Category category: categories) {
            if (category.getCipher().equals(cipher))
                return category;
        }
        return null;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category add(CategoryDTO categoryDTO) {
        String fileName = StorageService.uploadFile(categoryDTO.getFile());
        Category category = new Category();
        modelMapper.map(categoryDTO, category);
        category.setFileName(fileName);
        return save(category);
    }

    public Category update(CategoryDTO categoryDTO, Long id) {
        Category oldCategory = findById(id);
        String oldFileName = oldCategory.getFileName();
        String newFileName;

        try {
            newFileName = StorageService.uploadFile(categoryDTO.getFile());

            if (!Objects.equals(oldFileName, newFileName))
                StorageService.deleteFile(oldFileName);
        } catch (RuntimeException ignore) {
            newFileName = oldFileName;
        }

        Category category = new Category();
        modelMapper.map(categoryDTO, category);
        category.setId(id);
        category.setFileName(newFileName);

        return save(category);
    }

    public CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        modelMapper.map(category, categoryDTO);
        return categoryDTO;
    }

    public void deleteCategory(Long id) {
        Optional<Category> deleted = categoryRepository.findById(id);
        deleted.ifPresent(categoryRepository::delete);
    }

    public long count() {
        return categoryRepository.count();
    }
}
