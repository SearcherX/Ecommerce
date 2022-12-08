package home.ecommerce.repository;

import home.ecommerce.entity.Subcategory;
import org.springframework.data.repository.CrudRepository;

public interface SubcategoryRepository extends CrudRepository<Subcategory, Long> {
}
