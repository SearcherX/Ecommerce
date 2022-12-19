package home.ecommerce.repository;

import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long>, PagingAndSortingRepository<Image, Long> {
    List<Image> findByFileNameContainingAndProduct_SubcategoryOrderByProduct_price(String fileName, Subcategory subcategory, Pageable pageable);
    List<Image> findByProductAndFileNameContains(Product product, String str);
}
