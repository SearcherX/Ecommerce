package home.ecommerce.service;

import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.ProductRepository;
import home.ecommerce.repository.SubcategoryRepository;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private SubcategoryService subcategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageService imageService;

    @Test
    void findByIdWithImages() {
        Product product = productService.findByCipherFully("laptop-digma-eve-14");
        System.out.println(product);
    }

    @Test
    void findBySubcategoryWithoutImages() {
        Subcategory subcategory = subcategoryService.findById(1L);
        Page<Product> products = productService.findBySubcategoryWithMainImage(subcategory, 1);
        System.out.println(products);
    }

    @Test
    void findTop10Popular() {
        List<Product> top10 = productService.findTop10Popular();
        System.out.println(top10);
    }

    @Test
    void findTest() {
        Page<Product> products = productService.findTest("Ноутбуки", 1);
        System.out.println(products);
    }
}