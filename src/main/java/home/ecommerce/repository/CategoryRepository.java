package home.ecommerce.repository;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.service.StorageService;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long>, CategoryRepositoryCustom {
    long count();
    Category findByCipher(String cipher);
}
