package home.ecommerce.service;

import home.ecommerce.dto.ProductDTO;
import home.ecommerce.dto.SubcategoryDTO;
import home.ecommerce.entity.Product;
import home.ecommerce.entity.Subcategory;
import home.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Value("${spring.data.web.pageable.default-page-size}")
    private Integer pageSize;

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findBySubcategory(Subcategory subcategory, Integer offset) {
        return productRepository.findAllBySubcategoryOrderByPrice(subcategory, PageRequest.of(offset - 1, pageSize));
    }

    // метод формирования списка страниц для пагинации
    // пример [1,..., x-2, x-1, x, x+1, x+2, ...,last], где x - текущая страница, last - последняя
    public List<Integer> getPageNumbers(Subcategory subcategory, int currentPage) {
        int productCount = (int) productRepository.countBySubcategory(subcategory);
        int pageCount = productCount / pageSize + (productCount % pageSize == 0 ? 0 : 1);

        if (currentPage <= 0 || currentPage > pageCount)
            throw new RuntimeException("Нет такой страницы");

        int startPage;
        int endPage = currentPage + 2;

        // добавить страницы слева
        startPage = Math.max(currentPage - 2, 2);

        // добавить страницы справа
        if (currentPage + 2 > pageCount - 1) {
            endPage = currentPage;
        } else {
            if (currentPage >= endPage)
                endPage = currentPage + 2;
        }

        // проверить, чтобы страницы не вылезли за диапазон
        if (endPage > pageCount)
            endPage = pageCount;

        List<Integer> pageNumbers = new ArrayList<>();
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
