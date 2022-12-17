package home.ecommerce.service;

import home.ecommerce.dto.ProductDTO;
import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.entity.Image;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.ImageRepository;
import home.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ImageService imageService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    @Value("${spring.data.web.pageable.default-page-size}")
    private Integer pageSize;

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // метод загрузки товара со всеми зображениями
    @Transactional
    public Product findByCipherFully(String cipher) {
        Product product = productRepository.findByCipher(cipher);
        Hibernate.initialize(product.getFiles());
        Hibernate.initialize(product.getSubcategory().getCategory());
        return product;
    }

    // метод загрузки списка товаров только с главным изображением
    public List<Product> findBySubcategoryWithMainImage(Subcategory subcategory, Integer offset) {
        List<Product> products = productRepository.findBySubcategoryOrderByPrice(subcategory, PageRequest.of(offset - 1, pageSize));
        for (Product product : products) {
            Image image = imageService.findMainImageByProduct(product);

            //если нет изображения, то сделать пустым массив изображений
            List<Image> files = new ArrayList<>();

            if (image != null)
                files.add(image);

            product.setFiles(files);
        }
        return products;
    }


    // метод формирования списка страниц для пагинации
    // пример [1,..., x-2, x-1, x, x+1, x+2, ...,last], где x - текущая страница, last - последняя
    public List<Integer> getPageNumbers(Subcategory subcategory, int currentPage) {
        int productCount = (int) productRepository.countBySubcategory(subcategory);
        int pageCount = productCount / pageSize + (productCount % pageSize == 0 ? 0 : 1);
        int leftDif = 2;
        int rightDif = 2;

        if (pageCount <= 1)
            return new ArrayList<>(List.of(1));

        if (currentPage <= 0 || currentPage > pageCount)
            throw new RuntimeException("Нет такой страницы");

        int startPage;
        int endPage;

        // добавить страницы слева
        startPage = Math.max(currentPage - leftDif, 2);

        // добавить страницы справа
        endPage = Math.min(currentPage + rightDif, pageCount - 1);

        List<Integer> pageNumbers = new ArrayList<>();
        // в начало добавить первую страницу
        pageNumbers.add(1);

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        // в конец добавить последнюю страницу
        if (pageCount > endPage)
            pageNumbers.add(pageCount);

        return pageNumbers;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public ProductDTO toDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        modelMapper.map(product, productDTO);
        return productDTO;
    }

    public Product add(ProductDTO productDTO) {
        Product product = new Product();
        modelMapper.map(productDTO, product);
        return save(product);
    }

    public Product update(ProductDTO productDTO, Long id) {
        Product product = new Product();
        modelMapper.map(productDTO, product);
        product.setId(id);
        return save(product);
    }

    public void deleteProduct(Long id) {
        Optional<Product> deleted = productRepository.findById(id);
        deleted.ifPresent(productRepository::delete);
    }
}
