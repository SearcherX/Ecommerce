package home.ecommerce.repository;

import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    Page<Product> findBySubcategoryOrderByPrice(Subcategory subcategory, Pageable pageable);
    Page<Product> findByProductNameContainsIgnoreCaseOrderByPrice(String productName, Pageable pageable);
    Product findByCipher(String cipher);
    long countBySubcategory(Subcategory subcategory);
}
