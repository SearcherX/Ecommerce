package home.ecommerce.repository;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.service.StorageService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {
    private final StorageService storageService;
    @Override
    public void deleteImages(Category category) {
        for (Subcategory subcategory: category.getSubcategories()) {
            storageService.deleteFile(subcategory.getFileName());
        }
        storageService.deleteFile(category.getFileName());
    }
}
