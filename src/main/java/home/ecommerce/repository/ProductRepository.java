package home.ecommerce.repository;

import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import jakarta.persistence.PreRemove;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findBySubcategory(Subcategory subcategory);

}
