package home.ecommerce.service;

import home.ecommerce.dto.CategoryDTO;
import home.ecommerce.entity.Category;
import home.ecommerce.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;

    public List<Category> listAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category findByCipher(String cipher) {
        return categoryRepository.findByCipher(cipher);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category add(CategoryDTO categoryDTO) {
        String fileName = storageService.uploadFile(categoryDTO.getFile());
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
            newFileName = storageService.uploadFile(categoryDTO.getFile());

            if (!Objects.equals(oldFileName, newFileName))
                storageService.deleteFile(oldFileName);
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
        deleted.ifPresent(category -> {
            categoryRepository.deleteImages(category);
            categoryRepository.delete(category);
        });
    }

    public long count() {
        return categoryRepository.count();
    }
}
