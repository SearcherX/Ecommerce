package home.ecommerce.repository;

import home.ecommerce.entity.Category;
import home.ecommerce.entity.Subcategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {
    long count();
    List<Subcategory> findSubcategoriesByCategory(Category category);
    Subcategory findByCipher(String cipher);
    List<Subcategory> findAllByOrderBySubcategoryNameAsc();
}
