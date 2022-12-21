package home.ecommerce.repository;

import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Page<Product> findByOrderByPrice(Pageable pageable);
    Page<Product> findBySubcategoryOrderByPrice(Subcategory subcategory, Pageable pageable);
    Page<Product> findByProductNameContainsIgnoreCaseOrderByPrice(String productName, Pageable pageable);
    List<Product> findTop10ByOrderByPurchasesNumberDesc();
    List<Product> findTop10ByOrderByRegisterDateDesc();
    Product findByCipher(String cipher);
    long countBySubcategory(Subcategory subcategory);
}
