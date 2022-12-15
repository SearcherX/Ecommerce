package home.ecommerce.repository;

import home.ecommerce.entity.Image;
import home.ecommerce.entity.Subcategory;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ImageRepositoryTest {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Test
    void findAllByProduct_Subcategory() {
        Subcategory subcategory = subcategoryRepository.findById(1L).orElse(null);
        List<Image> images = imageRepository.findByFileNameContainingAndProduct_SubcategoryOrderByProduct_price("main",
                subcategory, PageRequest.of(0, 10));
//        List<Image> images = imageRepository.findImages(1L, PageRequest.of(0, 10));
        System.out.println(images);
    }
}