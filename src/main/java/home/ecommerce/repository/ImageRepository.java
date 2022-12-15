package home.ecommerce.repository;

import home.ecommerce.entity.Image;
import home.ecommerce.entity.Subcategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends CrudRepository<Image, Long>, PagingAndSortingRepository<Image, Long>,
        JpaRepository<Image, Long> {
    List<Image> findByFileNameContainingAndProduct_SubcategoryOrderByProduct_price(String fileName, Subcategory subcategory, Pageable pageable);
    @Query("select i from Image i " +
            "join i.product p " +
            "join p.subcategory s " +
            "where s.id=:subcategoryId and i.fileName like '%main%' " +
            "order by p.price")
    List<Image> findImages(@Param("subcategoryId") Long subcategoryId, Pageable pageable);
}
