package home.ecommerce.repository;

import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    List<Product> findBySubcategoryOrderByPrice(Subcategory subcategory, Pageable pageable);
    Product findByCipher(String cipher);
    long countBySubcategory(Subcategory subcategory);
}
