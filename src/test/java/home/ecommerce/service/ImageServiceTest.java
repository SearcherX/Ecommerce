package home.ecommerce.service;

import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ImageServiceTest {
@Autowired
private ImageService imageService;
@Autowired
private ProductService productService;
    @Test
    void findByProduct() {
        Product product = productService.findById(1L);
        List<Image> mainImages = imageService.findMainImageByProduct(product);
        System.out.println(mainImages);
    }
}